package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Region;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.BluGraphLayout;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public abstract class BasePAreaTaxonomyLayout extends BluGraphLayout {

    private final PAreaTaxonomy taxonomy;

    protected BasePAreaTaxonomyLayout(BluGraph graph, PAreaTaxonomy taxonomy) {
        super(graph);

        this.taxonomy = taxonomy;
    }
    
    protected ArrayList<Color> getTaxonomyLevelColors() {
        Color[] baseColors = new Color[]{
            new Color(178, 178, 178),
            new Color(55, 213, 102),
            new Color(121, 212, 250),
            new Color(242, 103, 103),
            new Color(232, 255, 114),
            Color.cyan,
            Color.orange,
            Color.pink,
            Color.green,
            Color.yellow,
            Color.BLUE,
            Color.MAGENTA
        };
        
        ArrayList<Color> colors = new ArrayList<>(Arrays.asList(baseColors));
        
        for(int c = 0 ; c < baseColors.length; c++) {
            colors.add(baseColors[c].brighter());
        }
        
        for(int c = 0 ; c < baseColors.length; c++) {
            colors.add(baseColors[c].darker());
        }
        
        return colors;
    }

    public void doLayout() {
        ArrayList<Area> sortedAreas = new ArrayList<>();    // Used for generating the graph
        ArrayList<Area> levelAreas = new ArrayList<>();     // Used for generating the graph

        ArrayList<Area> tempAreas = new ArrayList<>(taxonomy.getAreas());

        Area lastArea = null;

        Collections.sort(tempAreas, new Comparator<Area>() {    // Sort the areas based on the number of their relationships.

            public int compare(Area a, Area b) {
                if (a.getRelationships() == null || b.getRelationships() == null) {
                    return 0;
                }

                if (a.getRelationships().size() > b.getRelationships().size()) {
                    return 1;
                } else if (a.getRelationships().size() < b.getRelationships().size()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        for (Area a : tempAreas) {
            if (lastArea != null && lastArea.getRelationships().size() != a.getRelationships().size()) {
                Collections.sort(levelAreas, new Comparator<Area>() {    // Sort the areas based on concept count
                    public int compare(Area a, Area b) {
                        return a.getPAreas().size() - b.getPAreas().size();
                    }
                });

                int c = 0;

                for (c = 0; c < levelAreas.size(); c += 2) {
                    sortedAreas.add(levelAreas.get(c));
                }

                if (levelAreas.size() % 2 == 0) {
                    c = levelAreas.size() - 1;
                } else {
                    c = levelAreas.size() - 2;
                }

                for (; c >= 1; c -= 2) {
                    sortedAreas.add(levelAreas.get(c));
                }

                levelAreas.clear();
            }

            levelAreas.add(a);

            lastArea = a;
        }

        Collections.sort(levelAreas, new Comparator<Area>() {    // Sort the areas based on the number of their relationships.

            public int compare(Area a, Area b) {
                return a.getPAreas().size() - b.getPAreas().size();
            }
        });

        int c = 0;

        for (c = 0; c < levelAreas.size(); c += 2) {
            sortedAreas.add(levelAreas.get(c));
        }

        if (levelAreas.size() % 2 == 0) {
            c = levelAreas.size() - 1;
        } else {
            c = levelAreas.size() - 2;
        }

        for (; c >= 1; c -= 2) {
            sortedAreas.add(levelAreas.get(c));
        }
        
        this.setAreasInLayout(sortedAreas);
    }
    
    public void resetLayout() {
         ArrayList<Area> areas = this.getAreasInLayout();
         
         ArrayList<ArrayList<Area>> areasByLevel = new ArrayList<>();
         
         ArrayList<Area> level = new ArrayList<>();
         
         Area lastArea = null;
   
         for(Area area : areas) {
             if (lastArea != null && lastArea.getRelationships().size() != area.getRelationships().size()) {
                 areasByLevel.add(level);
                 
                 level = new ArrayList<>();
             }
             
             lastArea = area;
             level.add(area);
         }
         
         areasByLevel.add(level);
         
         int y = 40;
         
         for(ArrayList<Area> levelAreas : areasByLevel) {
             int maxHeight = 0;
             
             for(Area area : levelAreas) {
                 AreaEntry entry = this.getAreaEntries().get(area);
                 
                 entry.setLocation(entry.getX(), y);
                 
                 if(entry.getHeight() > maxHeight) {
                     maxHeight = entry.getHeight();
                 }
             }
             
             y += maxHeight + GraphLayoutConstants.CONTAINER_ROW_HEIGHT;
         }
    }
    
    protected PAreaEntry createPAreaPanel(BluGraph graph, PArea parea, RegionEntry parentRegionEntry, int x, int y, int pAreaX, GraphGroupLevel pAreaLevel) {
        PAreaEntry pareaEntry = new PAreaEntry(parea, graph, parentRegionEntry, pAreaX, pAreaLevel, new ArrayList<>());

        //Make sure this panel dimensions will fit on the graph, stretch the graph if necessary
        graph.stretchGraphToFitPanel(x, y, SinglyRootedNodeEntry.ENTRY_WIDTH, SinglyRootedNodeEntry.ENTRY_HEIGHT);

        //Setup the panel's dimensions, etc.
        pareaEntry.setBounds(x, y, SinglyRootedNodeEntry.ENTRY_WIDTH, SinglyRootedNodeEntry.ENTRY_HEIGHT);

        parentRegionEntry.add(pareaEntry, 0);

        return pareaEntry;
    }

    protected AreaEntry createAreaPanel(BluGraph graph,
            Area area,
            int x,
            int y,
            int width,
            int height,
            Color c,
            int areaX,
            GraphLevel parentLevel) {

        AreaEntry areaPanel = new AreaEntry(area, graph, areaX, parentLevel, new Rectangle(x, y, width, height));

        graph.stretchGraphToFitPanel(x, y, width, height);

        areaPanel.setBounds(x, y, width, height);
        areaPanel.setBackground(c);

        graph.add(areaPanel, 0);

        return areaPanel;
    }

    protected RegionEntry createRegionPanel(BluGraph graph, Region region, 
            AreaEntry parentAreaEntry, int x, int y, int width, int height, Color color, JLabel regionLabel) {

        RegionEntry regionPanel = new RegionEntry(region, 
                region.getName(),
                width, 
                height, 
                graph, 
                parentAreaEntry, 
                color, 
                regionLabel);

        graph.stretchGraphToFitPanel(x, y, width, height);

        regionPanel.setBounds(x, y, width, height);

        parentAreaEntry.add(regionPanel, 0);

        return regionPanel;
    }
    
    private void setAreasInLayout(ArrayList<Area> areas) {
        super.setLayoutGroupContainers(new ArrayList<>(areas));
    }
    
    public ArrayList<Area> getAreasInLayout() {
        return (ArrayList<Area>)(ArrayList<?>)super.getLayoutContainers();
    }
    
    public Map<Area, AreaEntry> getAreaEntries() {
        return (Map<Area, AreaEntry>)(Map<?,?>)super.getContainerEntries();
    }
    
    public Map<PArea, PAreaEntry> getPAreaEntries() {
        return (Map<PArea, PAreaEntry>)(Map<?,?>)super.getGroupEntries();
    }

}