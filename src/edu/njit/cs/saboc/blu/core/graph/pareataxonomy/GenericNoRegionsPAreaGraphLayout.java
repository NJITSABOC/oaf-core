package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Region;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLane;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.BLUGenericPAreaTaxonomyConfiguration;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public abstract class GenericNoRegionsPAreaGraphLayout<
        TAXONOMY_T extends PAreaTaxonomy,
        AREA_T extends Area<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T, REGION_T>,
        PAREA_T extends PArea<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T>,
        REGION_T extends Region<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T>,
        AREAENTRY_T extends GenericBluArea<AREA_T>,
        PAREAENTRY_T extends GenericBluPArea<PAREA_T, REGIONENTRY_T>,
        REGIONENTRY_T extends GenericBluRegion<REGION_T, AREAENTRY_T>,
        CONCEPT_T,
        REL_T,
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T>> 

        extends GenericPAreaGraphLayout <TAXONOMY_T, AREA_T, PAREA_T, REGION_T, 
            AREAENTRY_T, PAREAENTRY_T, REGIONENTRY_T, CONCEPT_T, REL_T, HIERARCHY_T> {
    
    private final BLUGenericPAreaTaxonomyConfiguration config;

    public GenericNoRegionsPAreaGraphLayout(BluGraph graph, TAXONOMY_T taxonomy, BLUGenericPAreaTaxonomyConfiguration config) {
        super(graph, taxonomy);
        
        this.config = config;
    }

    public void doLayout(boolean showConceptCounts) {
        super.doLayout();

        AREA_T lastArea = null;   // Used for generating the graph - this is the data version of an area
        AREAENTRY_T currentArea;    // Used for generating the graph - this is the graphical representation of an area
        GraphLevel currentLevel; // This is used as a temporary variable in this method to hold the current level.
        GraphGroupLevel currentPAreaLevel; // Used for generating the graph

        REGIONENTRY_T currentRegion;

        // These are a set of styles such that each new row is given a different color.
       ArrayList<Color> taxonomyLevelColors = getTaxonomyLevelColors();

        int areaX = 0;  // The first area on each line is given an areaX value of 0.
        int areaY = 0;  // The first row of areas is given an areaY value of 0.
        int pAreaX, pAreaY;
        int x = 0, y = 20, width = 0, maxHeight = 0;
        int style = 0;

        addGraphLevel(new GraphLevel(0, graph, new ArrayList<GraphLane>())); // Add the first level of areas (the single pArea 0-relationship level) to the data representation of the graph.

        for (AREA_T a : layoutGroupContainers) {  // Loop through the areas and generate the diagram for each of them
            AREAENTRY_T area;

            int x2;
            int y2;
            int regionX;
            int regionBump;
            
            int [] areaPAreaX;

            if (lastArea != null && lastArea.getRelationships().size() != a.getRelationships().size()) { // If a new row should be created...

                x = 0;  // Reset the x coordinate to the left
                y += maxHeight + GraphLayoutConstants.CONTAINER_ROW_HEIGHT; // Add the height of the tallest area to the y coordinate plus the areaRowHeight variable which defines how
                // much space should be between rows of areas.

                areaY++;    // Update the areaY variable to reflect the new row.
                areaX = 0;  // Reset the areaX variable.

                maxHeight = 0;  // Reset the maxHeight variable since this is a new row.
                style++;    // Update the style variable which is used to display different colors for the different rows.

                addGraphLevel(new GraphLevel(areaY, graph, generateUpperRowLanes(-5,
                        GraphLayoutConstants.CONTAINER_ROW_HEIGHT - 7, 3, null))); // Add a level object to the arrayList in the dataGraph object
            }

            width = 0;

            int pareaCount = 0;

            ArrayList<PAREA_T> areaPAreas = a.getAllPAreas();

            pareaCount = areaPAreas.size();
            
            // Take the number of cells and find the square root of it (rounded up) to
            // find the minimum width required for a square that could hold all the pAreas.

            int columnsWide = (int) Math.ceil(Math.sqrt(pareaCount)); 

            int regionWidth = columnsWide * (GenericGroupEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
            
            ArrayList<String> relNames = getAreaRelationshipNames(a);
            
            String areaName;
            
            if(relNames.isEmpty()) {
                areaName = "\u2205";
            } else {
                areaName = relNames.get(0);
                
                for(int c = 1; c < relNames.size(); c++) {
                    areaName += (", " + relNames.get(c));
                }
            }
            
            String countStr = "UNSET COUNT STR";
            
            ArrayList<PAREA_T> pareas = a.getAllPAreas();
            
            String pareaStr;
            
            if (pareas.size() == 1) {
                pareaStr = "1 Partial-area";
            } else {
                pareaStr = String.format("%d Partial-areas", pareas.size());
            }

            if(showConceptCounts) {
                HashSet<CONCEPT_T> concepts = a.getConcepts();
                
                String conceptStr;
                
                if(concepts.size() == 1) {
                    conceptStr = String.format("1 %s", config.getTextConfiguration().getConceptTypeName(false));
                } else {
                    conceptStr = String.format("%d %s", concepts.size(), config.getTextConfiguration().getConceptTypeName(true));
                }
                
                countStr = String.format("(%s, %s)", conceptStr, pareaStr);
                
            } else {
                countStr = String.format("(%s)", pareaStr);
            }
            
            areaName += (" " + countStr);

            final int MAX_RELS_DISPLAYED = 8;
            
            JLabel regionLabel = createRegionLabel(taxonomy, a.getRelationships(), countStr, regionWidth, MAX_RELS_DISPLAYED);
            
            regionWidth = Math.max(regionWidth, regionLabel.getWidth() + 8);

            width += regionWidth + 20;

            int height = regionLabel.getHeight() + (int) (Math.ceil((double) pareaCount / columnsWide))
                    * (GenericGroupEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT);  // Set the height to the greater of (a) the current height or (b) the number of regions in a column times the height of each pArea and the space between them.

            int maxRows = (int) Math.ceil(Math.sqrt(pareaCount)); // Update the maxRows variable.

            width += 20;
            height += 50 + GraphLayoutConstants.GROUP_ROW_HEIGHT;

            if (height > maxHeight) // Keeps track of the tallest cell on a row so that it knows how much lower to position the next row to avoid overlap.
            {
                maxHeight = height;
            }

            currentLevel = levels.get(areaY);

            Color color = taxonomyLevelColors.get(style % taxonomyLevelColors.size());

            area = createAreaPanel(a, x, y, width, height, color, areaX, currentLevel); // Create the area

            containerEntries.put(a.getId(), area);

            currentLevel.addContainerEntry(area);    // Add a data representation for this new area to the current area Level obj.

            addColumn(areaX, currentLevel.getLevelY(), generateColumnLanes(-3,
                    GraphLayoutConstants.CONTAINER_CHANNEL_WIDTH - 5, 3, null)); // Generates a column of lanes to the left of this area.
            
            currentArea = (AREAENTRY_T) currentLevel.getContainerEntries().get(areaX);

            regionX = GraphLayoutConstants.PARTITION_CHANNEL_WIDTH;
            areaPAreaX = new int[maxRows];
            
            regionBump = 0;

            REGIONENTRY_T r;

            pareaCount = areaPAreas.size();


            int horizontalPAreas;

            x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
            
            y2 = regionLabel.getHeight() + 30;

            pAreaX = 0;
            pAreaY = 0;
            
            horizontalPAreas = (int) Math.ceil(Math.sqrt(pareaCount));

            regionWidth = Math.max(regionWidth, horizontalPAreas * (GenericGroupEntry.ENTRY_WIDTH
                    + GraphLayoutConstants.GROUP_CHANNEL_WIDTH));
            
            int labelXPos =  GraphLayoutConstants.PARTITION_CHANNEL_WIDTH + (regionWidth - regionLabel.getWidth()) / 2;
            regionLabel.setLocation(labelXPos, 4);

            REGION_T region = a.getRegions().get(0);
            
            r = createRegionPanel(region, 
                    areaName, //labelLayout is the lines used..
                    area,
                    regionX - regionBump,
                    10,
                    regionWidth + GraphLayoutConstants.GROUP_CHANNEL_WIDTH + 10,
                    height - 20, 
                    color,
                    true,
                    regionLabel);

            regionBump++;
            
            currentRegion = (REGIONENTRY_T) currentArea.addPartitionEntry(r);

            currentRegion.addGroupLevel(new GraphGroupLevel(pAreaY, currentRegion)); // Add a new pAreaLevel to the data representation of the current Area object.

            currentArea.addRow(0, generateUpperRowLanes(-4,
                    GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, currentArea));

            int i = 0;

            for (PAREA_T p : areaPAreas) { // Draw the pArea inside this region
                PAREAENTRY_T pAreaPanel;

                currentPAreaLevel = currentRegion.getGroupLevels().get(pAreaY);

                pAreaPanel = createPAreaPanel(p, r, x2, y2, pAreaX, currentPAreaLevel);

                r.getVisibleGroups().add(pAreaPanel);

                currentRegion.addColumn(areaPAreaX[pAreaY], generateColumnLanes(-3,
                        GraphLayoutConstants.GROUP_CHANNEL_WIDTH - 2, 3, currentArea));
                
                groupEntries.put(p.getId(), pAreaPanel);    // Store it in a map keyed by its ID...

                currentPAreaLevel.addGroupEntry(pAreaPanel);

                if ((i + 1) % horizontalPAreas == 0 && i < pareaCount - 1) {
                    y2 += GenericGroupEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT;
                    x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    pAreaX = 0;
                    areaPAreaX[pAreaY]++;
                    pAreaY++;

                    if (currentRegion.getGroupLevels().size() <= pAreaY) {
                        currentRegion.addGroupLevel(new GraphGroupLevel(pAreaY, currentRegion)); // Add a new pAreaLevel to the data representation of the current Area object.
                        currentArea.addRow(pAreaY, generateUpperRowLanes(-4,
                                GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, currentArea));
                    }
                    
                } else {
                    x2 += (GenericGroupEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    pAreaX++;
                    areaPAreaX[pAreaY]++;
                }

                i++;
            }

            x += width + 40;  // Set x to a position after the newly created area and the appropriate space after that given the set channel width.
            areaX++;
            lastArea = a;
        }
        
        this.centerGraphLevels(this.getGraphLevels());
    }
}

