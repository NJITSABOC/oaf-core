package edu.njit.cs.saboc.blu.core.graph.edges;

import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.util.ArrayList;

/**
 * The data representation of a level of visual pArea objects within an Area.
 * @author David Daudelin
 */
public class GraphGroupLevel {

    /**
     * List of the pAreas in this level of the area.
     */
    private ArrayList<GenericGroupEntry> groups = new ArrayList<GenericGroupEntry>();

    /**
     * The index of this level in the pAreaLevels list of the GraphArea object that contains it.
     */
    private int groupLevelY;
    
    /**
     * The region this pAreaLevel is part of.
     */
    private GenericPartitionEntry parentPartition;
    
    private boolean isVisible;

    public GraphGroupLevel(int pY, GenericPartitionEntry parent) {
        groupLevelY = pY;
        parentPartition = parent;
        isVisible = true;
    }

    public int getGroupLevelY() {
        return groupLevelY;
    }

    public GenericPartitionEntry getParentPartition() {
        return parentPartition;
    }

    public ArrayList<GenericGroupEntry> getGroupEntries() {
        return groups;
    }

    public ArrayList<GraphLane> getRowAbove() {
        return parentPartition.getParentContainer().getRow(groupLevelY);
    }

    public void setRowAbove(ArrayList<GraphLane> r) {
        parentPartition.getParentContainer().setRow(groupLevelY, r);
    }

    /**
     * Adds the given pArea to the list of pAreas in this pArea Level object.
     * @param p
     */
    public void addGroupEntry(GenericGroupEntry p) {
        groups.add(p);
    }

    public boolean removeGroupEntry(GenericGroupEntry p) {
        return groups.remove(p);
    }

    public void replaceGroupEntry(GenericGroupEntry replacee, GenericGroupEntry replacer) {
        groups.set(groups.indexOf(replacee), replacer);
    }

    public int getGroupEntryCount() {
        return groups.size();
    }

    public int getVisibleGroupCount() {
        int visibleGroupCount = 0;

        for (GenericGroupEntry x : groups) {
            if (x.isVisible()) {
                ++visibleGroupCount;
            }
        }

        return visibleGroupCount;
    }

    public String toString() {
        String result = "";

        for (int i = 0; i < groups.size(); ++i) {
            result += groups.get(i).toString() + "\n";
        }

        return result;
    }

    public void setVisible(boolean value) {
        isVisible = value;
    }

    public boolean getIsVisible() {
        return isVisible;
    }
}
