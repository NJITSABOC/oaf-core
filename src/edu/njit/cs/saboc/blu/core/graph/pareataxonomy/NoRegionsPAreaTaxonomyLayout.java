package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Region;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public class NoRegionsPAreaTaxonomyLayout extends BasePAreaTaxonomyLayout {
    
    private final PAreaTaxonomyConfiguration config;

    public NoRegionsPAreaTaxonomyLayout(
            PAreaBluGraph graph, 
            PAreaTaxonomy taxonomy, 
            PAreaTaxonomyConfiguration config) {
        
        super(graph, taxonomy);
        
        this.config = config;
    }

    public void doLayout() {
        super.doLayout();
        
        BluGraph graph = super.getGraph();

        Area lastArea = null;   // Used for generating the graph - this is the data version of an area
        AreaEntry currentAreaEntry;    // Used for generating the graph - this is the graphical representation of an area
        GraphLevel currentLevel; // This is used as a temporary variable in this method to hold the current level.
        GraphGroupLevel currentPAreaLevel; // Used for generating the graph

        RegionEntry currentRegion;

        // These are a set of styles such that each new row is given a different color.
       ArrayList<Color> taxonomyLevelColors = getTaxonomyLevelColors();

        int areaX = 0;  // The first area on each line is given an areaX value of 0.
        int areaY = 0;  // The first row of areas is given an areaY value of 0.
        int pAreaX, pAreaY;
        int x = 0, y = 20, width = 0, maxHeight = 0;
        int style = 0;

        addGraphLevel(new GraphLevel(0, graph, new ArrayList<>())); // Add the first level of areas (the single pArea 0-relationship level) to the data representation of the graph.

        for (Area area : super.getAreasInLayout()) {  // Loop through the areas and generate the diagram for each of them
            AreaEntry areaEntry;

            int x2;
            int y2;
            int regionX;
            int regionBump;
            
            int [] areaPAreaX;

            if (lastArea != null && lastArea.getRelationships().size() != area.getRelationships().size()) { // If a new row should be created...

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

            ArrayList<PArea> areaPAreas = new ArrayList<>(area.getPAreas());
            
            areaPAreas.sort( (a, b) -> { 
                if(a.getConceptCount() == b.getConceptCount()) {
                    return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
                } else {
                    return b.getConceptCount() - a.getConceptCount();
                }
            });

            pareaCount = areaPAreas.size();
            
            // Take the number of cells and find the square root of it (rounded up) to
            // find the minimum width required for a square that could hold all the pAreas.
            int columnsWide = (int) Math.ceil(Math.sqrt(pareaCount)); 

            int regionWidth = columnsWide * (SinglyRootedNodeEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);

            JLabel areaLabel = createAreaLabel(area, regionWidth);
            
            regionWidth = Math.max(regionWidth, areaLabel.getWidth() + 8);

            width += regionWidth + 20;

            int height = areaLabel.getHeight() + (int) (Math.ceil((double) pareaCount / columnsWide))
                    * (SinglyRootedNodeEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT);  // Set the height to the greater of (a) the current height or (b) the number of regions in a column times the height of each pArea and the space between them.

            int maxRows = (int) Math.ceil(Math.sqrt(pareaCount)); // Update the maxRows variable.

            width += 20;
            height += 50 + GraphLayoutConstants.GROUP_ROW_HEIGHT;

            // Keeps track of the tallest cell on a row so that it knows how much lower to position the next row to avoid overlap.
            if (height > maxHeight) {
                maxHeight = height;
            }

            currentLevel = super.getLevels().get(areaY);

            Color color = taxonomyLevelColors.get(style % taxonomyLevelColors.size());

            areaEntry = createAreaPanel(graph, area, x, y, width, height, color, areaX, currentLevel); // Create the area
            
            super.getAreaEntries().put(area, areaEntry);

            currentLevel.addContainerEntry(areaEntry);    // Add a data representation for this new area to the current area Level obj.

            addColumn(areaX, currentLevel.getLevelY(), generateColumnLanes(-3,
                    GraphLayoutConstants.CONTAINER_CHANNEL_WIDTH - 5, 3, null)); // Generates a column of lanes to the left of this area.
            
            currentAreaEntry = (AreaEntry)currentLevel.getContainerEntries().get(areaX);

            regionX = GraphLayoutConstants.PARTITION_CHANNEL_WIDTH;
            areaPAreaX = new int[maxRows];
            
            regionBump = 0;

            pareaCount = areaPAreas.size();
            int horizontalPAreas;

            x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
            
            y2 = areaLabel.getHeight() + 30;

            pAreaX = 0;
            pAreaY = 0;
            
            horizontalPAreas = (int) Math.ceil(Math.sqrt(pareaCount));

            regionWidth = Math.max(regionWidth, horizontalPAreas * (SinglyRootedNodeEntry.ENTRY_WIDTH
                    + GraphLayoutConstants.GROUP_CHANNEL_WIDTH));
            
            int labelXPos =  GraphLayoutConstants.PARTITION_CHANNEL_WIDTH + (regionWidth - areaLabel.getWidth()) / 2;
            areaLabel.setLocation(labelXPos, 4);

            RegionEntry regionEntry = createRegionPanel(
                    graph,
                    new Region(area, area.getPAreas(), area.getRelationships()), 
                    areaEntry,
                    regionX - regionBump,
                    10,
                    regionWidth + GraphLayoutConstants.GROUP_CHANNEL_WIDTH + 10,
                    height - 20, 
                    color,
                    areaLabel);

            regionBump++;
            
            currentRegion = (RegionEntry)currentAreaEntry.addPartitionEntry(regionEntry);

            currentRegion.addGroupLevel(new GraphGroupLevel(pAreaY, currentRegion)); // Add a new pAreaLevel to the data representation of the current Area object.

            currentAreaEntry.addRow(0, generateUpperRowLanes(-4,
                    GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, currentAreaEntry));

            int i = 0;

            for (PArea parea : areaPAreas) { // Draw the pArea inside this region
                PAreaEntry pareaEntry;

                currentPAreaLevel = currentRegion.getGroupLevels().get(pAreaY);

                pareaEntry = createPAreaPanel(graph, parea, regionEntry, x2, y2, pAreaX, currentPAreaLevel);

                regionEntry.getVisibleGroups().add(pareaEntry);

                currentRegion.addColumn(areaPAreaX[pAreaY], generateColumnLanes(-3,
                        GraphLayoutConstants.GROUP_CHANNEL_WIDTH - 2, 3, currentAreaEntry));
                
                super.getGroupEntries().put(parea, pareaEntry);
                
                currentPAreaLevel.addGroupEntry(pareaEntry);

                if ((i + 1) % horizontalPAreas == 0 && i < pareaCount - 1) {
                    y2 += SinglyRootedNodeEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT;
                    x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    pAreaX = 0;
                    areaPAreaX[pAreaY]++;
                    pAreaY++;

                    if (currentRegion.getGroupLevels().size() <= pAreaY) {
                        currentRegion.addGroupLevel(new GraphGroupLevel(pAreaY, currentRegion)); // Add a new pAreaLevel to the data representation of the current Area object.
                        currentAreaEntry.addRow(pAreaY, generateUpperRowLanes(-4,
                                GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, currentAreaEntry));
                    }
                    
                } else {
                    x2 += (SinglyRootedNodeEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    pAreaX++;
                    areaPAreaX[pAreaY]++;
                }

                i++;
            }

            x += width + 40;  // Set x to a position after the newly created area and the appropriate space after that given the set channel width.
            areaX++;
            lastArea = area;
        }
        
        this.centerGraphLevels(this.getGraphLevels());
    }
    
    @Override
    public JLabel createPartitionLabel(PartitionedNode partition, int width) {
        Region region = (Region)partition;
        
        return createAreaLabel(region.getArea(), width);
    }

    private JLabel createAreaLabel(Area area, int width) {
        String pareaStr;

        if (area.getPAreas().size() == 1) {
            pareaStr = "1 Partial-area";
        } else {
            pareaStr = String.format("%d Partial-areas", area.getPAreas().size());
        }

        String countStr;

        Set<Concept> concepts = area.getConcepts();

        String conceptStr;

        if (concepts.size() == 1) {
            conceptStr = String.format("1 %s", config.getTextConfiguration().getConceptTypeName(false));
        } else {
            conceptStr = String.format("%d %s", concepts.size(), config.getTextConfiguration().getConceptTypeName(true));
        }

        countStr = String.format("(%s, %s)", conceptStr, pareaStr);

        final int MAX_RELS_DISPLAYED = 8;

        Canvas canvas = new Canvas();
        FontMetrics fontMetrics = canvas.getFontMetrics(new Font("SansSerif", Font.BOLD, 14));
        
        Set<InheritableProperty> properties = area.getRelationships();

        String[] entries;

        if (properties.isEmpty()) {
            entries = new String[]{"\u2205", countStr};
        } else {
            entries = new String[properties.size()];

            int c = 0;

            int longestRelNameWidth = -1;

            for (InheritableProperty rel : properties) {
                String relName = rel.getName();

                int relNameWidth = fontMetrics.stringWidth(relName);

                if (relNameWidth > longestRelNameWidth) {
                    longestRelNameWidth = relNameWidth;
                }

                entries[c++] = relName;
            }

            Arrays.sort(entries);

            entries = Arrays.copyOf(entries, entries.length + 1);

            entries[entries.length - 1] = countStr;

            if (fontMetrics.stringWidth(countStr) > longestRelNameWidth) {
                longestRelNameWidth = fontMetrics.stringWidth(countStr);
            }

            if (properties.size() > 1) {
                longestRelNameWidth += fontMetrics.charWidth(',');
            }

            if (longestRelNameWidth > width) {
                width = longestRelNameWidth + 4;
            }
        }

        return this.createFittedPartitionLabel(entries, width, fontMetrics);
    }
}
