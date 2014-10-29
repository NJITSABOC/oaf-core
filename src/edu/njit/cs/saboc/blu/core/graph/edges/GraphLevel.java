package edu.njit.cs.saboc.blu.core.graph.edges;

import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericContainerEntry;
import java.util.ArrayList;

/**
 * This class is a data representation of the graphical representation of a level of areas.
 * @author David Daudelin
 */
public class GraphLevel {

    /**
     * List of the areas in a given level
     */
    private ArrayList<GenericContainerEntry> containerEntries =
            new ArrayList<GenericContainerEntry>();

    /**
     * List of the lanes above this Level.
     */
    private ArrayList<GraphLane> rowAbove;

    /**
     * Index of this level in the <i>levels</i> arrayList from Graph
     */
    private int levelY;
    
    /**
     * The graph object this level is part of.
     */
    private BluGraph parentGraph;

    public GraphLevel(int lY, BluGraph parent, ArrayList<GraphLane> above) {
        levelY = lY;
        parentGraph = parent;
        rowAbove = above;
    }
    
    public int getWidth() {
        int width = 0;
        
        for(GenericContainerEntry entry : containerEntries) {
            width += entry.getWidth();
        }
        
        width += (containerEntries.size() - 1) * GraphLayoutConstants.CONTAINER_CHANNEL_WIDTH;
        
        return width;
    }

    public int getHeight() {
        int result = 0;

        for (int i = 0; i < containerEntries.size(); i++) {
            if (containerEntries.get(i).getHeight() > result) {
                result = containerEntries.get(i).getHeight();
            }
        }

        return result;
    }

    public int getHighestLane() {
        int highestLane = 0;

        for (GraphLane l : rowAbove) {
            highestLane = Math.max(highestLane, l.getPosY() + l.getSize() / 2);
        }

        return highestLane;
    }

    public void cascadeHeightChange(int h) {
        int dh = getHeight() - h;

        for (int currentGraphLevel = this.getLevelY() + 1; currentGraphLevel < parentGraph.getLevels().size(); ++currentGraphLevel) {
            for (GenericContainerEntry containerEntry : parentGraph.getLevels().get(currentGraphLevel).getContainerEntries()) {
                containerEntry.setBounds(containerEntry.getX(), containerEntry.getY() - dh, containerEntry.getWidth(), containerEntry.getHeight());
            }
        }
    }

    public int getLevelY() {
        return levelY;
    }

    /*
     * Returns the upper y coordinate of the level based on the highest area it contains.
     */
    public int getY() {
        int y = Integer.MAX_VALUE;

        for (GenericContainerEntry a : containerEntries) {
            if (a.getPosY() < y) {
                y = a.getPosY();
            }
        }

        return y;
    }

    public BluGraph getParentGraph() {
        return parentGraph;
    }

    public ArrayList<GenericContainerEntry> getContainerEntries() {
        return containerEntries;
    }

    public ArrayList<GraphLane> getRowAbove() {
        return rowAbove;
    }

    public void setRowAbove(ArrayList<GraphLane> r) {
        rowAbove = r;
    }

    /**
     * Adds the given area to the list of areas contained in this Level object.
     * @param a
     */
    public void addContainerEntry(GenericContainerEntry entry) {
        containerEntries.add(entry);
    }

    public GenericContainerEntry getTallestContainerEntry() {
        GenericContainerEntry tallestArea = null;

        for (GenericContainerEntry containerEntry : containerEntries) {
            if (tallestArea == null) {
                tallestArea = containerEntry;
            } else if (containerEntry.getHeight() > tallestArea.getHeight()) {
                tallestArea = containerEntry;
            }
        }

        return tallestArea;
    }
}
