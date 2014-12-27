package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericRegion;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.BluGraphLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public abstract class GenericPAreaGraphLayout<
        TAXONOMY_T extends GenericPAreaTaxonomy,
        AREA_T extends GenericArea<CONCEPT_T, REL_T, PAREA_T, REGION_T>,
        PAREA_T extends GenericPArea<CONCEPT_T, REL_T>,
        REGION_T extends GenericRegion<CONCEPT_T, REL_T, PAREA_T>,
        AREAENTRY_T extends GenericBluArea<AREA_T>,
        PAREAENTRY_T extends GenericBluPArea<PAREA_T, REGIONENTRY_T>,
        REGIONENTRY_T extends GenericBluRegion<REGION_T, AREAENTRY_T>,
        CONCEPT_T,
        REL_T> extends BluGraphLayout<AREA_T, AREAENTRY_T, PAREAENTRY_T> {

    protected TAXONOMY_T taxonomy;

    protected GenericPAreaGraphLayout(BluGraph graph, TAXONOMY_T taxonomy) {
        super(graph);

        this.taxonomy = taxonomy;
    }

    public void doLayout() {
        ArrayList<AREA_T> sortedAreas = new ArrayList<AREA_T>();    // Used for generating the graph
        ArrayList<AREA_T> levelAreas = new ArrayList<AREA_T>();     // Used for generating the graph

        ArrayList<AREA_T> tempAreas = taxonomy.getAreas();

        AREA_T lastArea = null;

        Collections.sort(tempAreas, new Comparator<AREA_T>() {    // Sort the areas based on the number of their relationships.

            public int compare(AREA_T a, AREA_T b) {
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

        for (AREA_T a : tempAreas) {
            if (lastArea != null && lastArea.getRelationships().size() != a.getRelationships().size()) {
                Collections.sort(levelAreas, new Comparator<AREA_T>() {    // Sort the areas based on concept count

                    public int compare(AREA_T a, AREA_T b) {
                        if (a.getAllPAreas().size() > b.getAllPAreas().size()) {
                            return 1;
                        } else if (a.getAllPAreas().size() < b.getAllPAreas().size()) {
                            return -1;
                        } else {
                            return 0;
                        }
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

        Collections.sort(levelAreas, new Comparator<AREA_T>() {    // Sort the areas based on the number of their relationships.

            public int compare(AREA_T a, AREA_T b) {
                if (a.getAllPAreas().size() > b.getAllPAreas().size()) {
                    return 1;
                } else if (a.getAllPAreas().size() < b.getAllPAreas().size()) {
                    return -1;
                } else {
                    return 0;
                }
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

        lastArea = null;
        layoutGroupContainers = sortedAreas;
    }

    public ArrayList<AREA_T> getLayoutAreas() {
        return layoutGroupContainers;
    }

    /**
     * Creates a JPanel representing a pArea, adds it to the graph, and returns it.
     * @param p The data object for this pArea
     * @param parent The region which this pArea is being put inside
     * @param x The x coordinate of the pArea
     * @param y The y coordinate of the pArea
     * @param pAreaX The horizontal index of this pArea within a pArea level (the pArea all the way on the left in a row of pAreas in a region is index 0, etc.)
     * @param pAreaLevel The object representing the pArea level this pArea will be added to.
     * @return A BluOWLPArea object representing the newly created JPanel for this pArea
     */
    protected abstract PAREAENTRY_T createPAreaPanel(PAREA_T p, REGIONENTRY_T parent, int x, int y, int pAreaX, GraphGroupLevel pAreaLevel);

    /**
     * Creates a JPanel representing an Area, adds it to the graph, and returns it.
     * @param a The data object representing this area.
     * @param x The x coordinate of the pArea
     * @param y The y coordinate of the pArea
     * @param width The width of this area in pixels.
     * @param height The height of this area in pixels.
     * @param c The background color for this area.
     * @param areaX The horizontal index of this area within a level (the area all the way on the left in a row of areas in a region is index 0, etc.)
     * @param parentLevel The object representing the level this area will be added to.
     * @return A BluArea object representing the newly created JPanel for this area.
     */
    protected abstract AREAENTRY_T createAreaPanel(AREA_T a, int x, int y, int width, int height, Color c, int areaX, GraphLevel parentLevel);

    /**
     * Creates a JPanel representing a region, adds it to the graph, and returns it.
     * @param region The data object representing this region.
     * @param regionName The title for this region.
     * @param ap The parent area this region is inside.
     * @param x The x-coordinate where this region is to be positioned.
     * @param y The y-coordinate where this region is to be positioned.
     * @param width The width of this region in pixels.
     * @param height The height of this region in pixels.
     * @param c The background color of this region.
     * @return A BluRegion object representing the newly created JPanel for this region.
     */
    protected abstract REGIONENTRY_T createRegionPanel(REGION_T region, String regionName,
            AREAENTRY_T ap, int x, int y, int width, int height, Color c, boolean treatRegionAsArea, JLabel label);
     /**
     * Returns an area from a given level in this graph.
     * @param level Specifies the level at which to retrieve the area
     * @param areaX Specifies which area to retrieve in the level (where the first area in the level is index 0)
     * @return The area at that level and horizontal position.
     */
    public AREAENTRY_T getArea(int level, int areaX) {
        return (AREAENTRY_T)getConainterAt(level, areaX);
    }

    /**
     * Returns a region inside a given area in this graph.
     * @param level Specifies the level at which to retrieve the area
     * @param areaX Specifies which area to retrieve in the level (where the first area in the level is index 0)
     * @param regionX Specifies which region to retrieve in the area (where, again, the first region in the area is index 0)
     * @return The region at the given position.
     */
    public REGIONENTRY_T getRegion(int level, int areaX, int regionX) {
        return (REGIONENTRY_T)getContainerPartitionAt(level, areaX, regionX);
    }

    /**
     * Returns a pArea inside a given region in this graph.
     * @param level Specifies the level at which to retrieve the area
     * @param areaX Specifies which area to retrieve in the level (where the first area in the level is index 0)
     * @param regionX Specifies which region to retrieve in the area (where, again, the first region in the area is 0)
     * @param pAreaY Specifies which level of pAreas to retrieve it from (where the first level is index 0)
     * @param pAreaX Specifies which pArea to retrieve in a given pArea level (where the first pArea in the level is index 0).
     * @return
     */
    public PAREAENTRY_T getPArea(int level, int areaX, int regionX, int pAreaY, int pAreaX) {
        return (PAREAENTRY_T)getGroupEntry(level, areaX, regionX, pAreaY, pAreaX);
    }
    
    protected abstract ArrayList<String> getAreaRelationshipNames(AREA_T a);
    
    protected abstract JLabel createRegionLabel(TAXONOMY_T taxonomy, HashSet<REL_T> relationships, String countString, int width, int maxProperties);
}