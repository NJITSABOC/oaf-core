package edu.njit.cs.saboc.blu.core.graph.layout;

import SnomedShared.generic.GenericContainerPartition;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLane;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericContainerEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Chris
 */
public abstract class BluGraphLayout<T extends GenericGroupContainer,
        V extends GenericContainerEntry, U extends GenericGroupEntry>   {

    protected BluGraph graph;

    protected ArrayList<T> layoutGroupContainers = new ArrayList<T>();

    protected ArrayList<GraphLevel> levels = new ArrayList<GraphLevel>();

    protected ArrayList<GraphLane>[][] columns = new ArrayList[500][50];

    protected HashMap<Integer, V> containerEntries = new HashMap<Integer, V>();

    /**
     * This maps the concept identifiers for pArea parents to objects
     * representing the graphical display of those areas.
     */
    protected HashMap<Integer, U> groupEntries = new HashMap<Integer, U>();

    protected BluGraphLayout(BluGraph graph) {
        this.graph = graph;
    }

    protected abstract void doLayout();
    
    public abstract JLabel createPartitionLabel(GenericContainerPartition partition, int width);
    
    public ArrayList<GraphLevel> getGraphLevels() {
        return levels;
    }

    protected GenericContainerEntry getConainterAt(int level, int areaX) {
        return levels.get(level).getContainerEntries().get(areaX);
    }

    protected GenericPartitionEntry getContainerPartitionAt(int level, int areaX, int regionX) {
        return levels.get(level).getContainerEntries().get(areaX).getContainerPartitions().get(regionX);
    }

    protected GenericGroupEntry getGroupEntry(int level, int areaX, int regionX, int pAreaY, int pAreaX) {
        return levels.get(level).getContainerEntries().get(areaX).getContainerPartitions().get(regionX).getGroupLevels().get(pAreaY).getGroupEntries().get(pAreaX);
    }

    public HashMap<Integer, U> getGroupEntries() {
        return groupEntries;
    }

    public HashMap<Integer, V> getContainerEntries() {
        return containerEntries;
    }

    /**
     * Adds the given level to the arrayList containing the levels in this Graph.
     * @param l
     */
    protected void addGraphLevel(GraphLevel l) {
        levels.add(l);
    }

    /**
     * This method creates and returns an arraylist of lanes creating a column above a given y coordinate.
     * @param y The vertical coordinate (in pixels) of the lower edge of the row.
     * @param height The height of the row in pixels
     * @param laneHeight The height of each lane in pixels
     * @return The array list holding all the available lanes.
     */
    protected ArrayList<GraphLane> generateUpperRowLanes(int y, int height, int laneHeight, GenericContainerEntry parent) {
        int spaceUsed = 0;

        ArrayList<GraphLane> result = new ArrayList<GraphLane>();

        while (spaceUsed + laneHeight <= height) {
            result.add(new GraphLane(-1, y, laneHeight, parent));
            y = y - laneHeight;
            spaceUsed += laneHeight;
        }

        return result;
    }

    /**
     * This method creates and returns an arraylist of lanes creating a column to the left of a given x coordinate.
     * @param x The horizontal coordinate (in pixels) of the right edge of the column.
     * @param width The width of the column in pixels
     * @param laneWidth The width of each lane in pixels
     * @return The array list holding all the available lanes.
     */
    protected ArrayList<GraphLane> generateColumnLanes(int x, int width, int laneWidth, GenericContainerEntry parent) {
        int spaceUsed = 0;

        ArrayList<GraphLane> result = new ArrayList<GraphLane>();

        while (spaceUsed + laneWidth <= width) {
            result.add(new GraphLane(x, -1, laneWidth, parent));
            x = x - laneWidth;
            spaceUsed += laneWidth;
        }

        return result;
    }

    /**
     * Adds a column to the graph's 2D array of columns and adjusts the size of it, if necessary.
     * @param x The x index of the requested column.
     * @param y The y index of the requested column.
     * @param col The column to be added.
     */
    protected void addColumn(int x, int y, ArrayList<GraphLane> col) {
        
        if(x >= columns.length) {
             ArrayList<GraphLane>[][] columnsCopy = new ArrayList[(int)(x * 1.5)][];
             
             for(int c = 0; c < columns.length; c++) {
                 columnsCopy[c] = columns[c];
             }
             
             columns = columnsCopy;
        }
        
        if(columns[x] == null) {
            columns[x] = new ArrayList[50];
        }
        
        if(y >= columns[x].length) {
            ArrayList<GraphLane>[] rowCopy = new ArrayList[(int)(y * 1.5)];
            
            for(int c = 0; c < columns[x].length; c++) {
                rowCopy[c] = columns[x][c];
            }
            
            columns[x] = rowCopy;
        }
        
        if(columns.length <= x || columns[x].length <= y) {
            System.out.println("ERROR:" + x + " | " + y);
        }

        columns[x][y] = col;
    }


    /**
     * Returns a column between areas in the graph with the specified x and y indexes, if it exists. Else, it returns null.
     * @param x The x index of the requested column.
     * @param y The y index of the requested column.
     * @return An ArrayList of lanes representing the column with the specified x and y indexes, if it exists. Else, it returns null.
     */
    public ArrayList<GraphLane> getColumn(int x, int y) {
        if (columns.length < x || columns[x].length < y) {
            return null;
        }

        return columns[x][y];
    }


    /**
     * Resize a pArea row (the rows between pAreas inside an area) to add more lanes and redraw the edges.
     * @param road The ArrayList of GraphLane objects that make up the row currently.
     * @param level The level of the pArea row that is being resized.
     * @param newLanes The number of new lanes to create.
     * @param pArea A pArea below the row being resized.
     * @return The resized road.
     */
    public ArrayList<GraphLane> resizeGroupRow(ArrayList<GraphLane> road, int level, int newLanes, GenericGroupEntry group) {
        int laneHeight = road.get(0).getSize();
        int bump = laneHeight * newLanes;
        int lowerBound = road.get(road.size() - 1).getPosY() - laneHeight;

        // Add new lanes
        road.addAll(road.size(), generateUpperRowLanes(lowerBound, bump, laneHeight, group.getGroupLevelParent().getParentPartition().getParentContainer()));
        group.getGroupLevelParent().setRowAbove(road);

        int s = 0;
        int pAreaLevel = group.getGroupLevelParent().getGroupLevelY();
        GraphGroupLevel l;
        GenericGroupEntry p;

        GenericContainerEntry parentContainer = group.getParentContainer();

        // If the area is now the tallest area in the level, bump down the areas below it.
        if (parentContainer.getHeight() + bump > parentContainer.getParentLevel().getHeight()) {
            bumpDownContainersAt(parentContainer.getParentLevel().getLevelY() + 1, bump / 2);
        }

        resizeBox(parentContainer, parentContainer.getWidth(), parentContainer.getHeight() + bump);

        // Resize the regions and bump down any necessary pAreas.
        for (GenericPartitionEntry r : parentContainer.getContainerPartitions()) {

            resizeBox(r, r.getWidth(), r.getHeight() + bump);

            for (int n = pAreaLevel; n < r.getGroupLevels().size(); n++) {
                l = r.getGroupLevels().get(n);
                s += l.getGroupEntries().size();
            }

            JPanel[] pBelow = new JPanel[s];
            int i2 = 0;

            for (int n = pAreaLevel; n < r.getGroupLevels().size(); n++) {
                l = r.getGroupLevels().get(n);

                for (int i3 = 0; i3 < l.getGroupEntries().size(); i3++) {
                    p = l.getGroupEntries().get(i3);
                    pBelow[i2] = p;
                    i2++;
                }
            }

            moveBoxes(pBelow, 0, bump);
        }

        graph.redrawEdges();

        return road;
    }

    /**
     * Resize a pArea column (the columns between pAreas inside an area) to add more lanes and redraw the edges.
     * @param road The ArrayList of GraphLane objects that make up the column currently.
     * @param newLanes The number of new lanes to create.
     * @param pArea A pArea below the row being resized.
     * @return The resized road.
     */
    public ArrayList<GraphLane> resizeGroupColumn(ArrayList<GraphLane> road,
            int newLanes, GenericGroupEntry groupEntry) {
        
        int laneWidth = road.get(0).getSize();
        int bump = laneWidth * newLanes;
        int rightBound = road.get(road.size() - 1).getPosX() - laneWidth;

        GenericContainerEntry parentContainer = groupEntry.getParentContainer();
        GenericPartitionEntry parentPartition = groupEntry.getParentPartition();

        road.addAll(road.size(), generateColumnLanes(rightBound, bump, laneWidth, parentContainer));

        resizeBox(parentContainer, parentContainer.getWidth() + bump, parentContainer.getHeight());
        resizeBox(parentPartition, parentPartition.getWidth() + bump, parentPartition.getHeight());

        int tempIndex = parentContainer.getContainerPartitions().indexOf(parentPartition);

        for (int i = tempIndex + 1; i < parentContainer.getContainerPartitions().size(); i++) {
            GenericPartitionEntry r = parentContainer.getContainerPartitions().get(i);
            moveBox(r, bump, 0);
        }

        ArrayList<JPanel> groupsTemp = new ArrayList<JPanel>();

        for (GraphGroupLevel pL : parentPartition.getGroupLevels()) {
            for (int i = groupEntry.getGroupX(); i < pL.getGroupEntries().size(); i++) {
                groupsTemp.add((JPanel) pL.getGroupEntries().get(i));
            }
        }

        moveBoxes(groupsTemp, bump / 2, 0);

        GenericContainerEntry tempContainer;
        ArrayList<JPanel> containersTemp = new ArrayList<JPanel>();

        for (int i = parentContainer.getContainerX() + 1; i < parentContainer.getLevelParent().getContainerEntries().size(); i++) {
            tempContainer = parentContainer.getLevelParent().getContainerEntries().get(i);
            containersTemp.add(tempContainer);
        }

        moveBoxes(containersTemp, bump / 2, 0);

        graph.redrawEdges();

        return road;
    }

    /**
     * Resizes a column between areas, adding new lanes, and returns the updated one.
     * @param road The ArrayList of GraphLane objects that make up the column currently.
     * @param newLanes The number of new lanes to create.
     * @param area The area to the right of the column being resized.
     * @return The resized road.
     */
    public ArrayList<GraphLane> resizeColumn(ArrayList<GraphLane> road, int newLanes, GenericContainerEntry containerEntry) {
        int laneWidth = road.get(0).getSize();
        int bump = laneWidth * newLanes;
        int rightBound = road.get(road.size() - 1).getPosX() - laneWidth;

        road.addAll(road.size(), generateColumnLanes(rightBound, bump, laneWidth, containerEntry));

        addColumn(containerEntry.getContainerX(), containerEntry.getLevelParent().getLevelY(), road);

        GraphLevel parentLevel = containerEntry.getLevelParent();

        JPanel[] containerEntries = new JPanel[parentLevel.getContainerEntries().size() - containerEntry.getContainerX()];

        for (int i = containerEntry.getContainerX(); i < parentLevel.getContainerEntries().size(); i++) {
            GenericContainerEntry temp = parentLevel.getContainerEntries().get(i);
            containerEntries[i - containerEntry.getContainerX()] = temp;
        }

        moveBoxes(containerEntries, bump, 0);

        graph.redrawEdges();

        return road;
    }

    /**
     * Resizes a row between areas, adding more lanes, and returns the new road.
     * @param road The ArrayList of GraphLane objects that make up the column currently.
     * @param level The level of the row being resized.
     * @param newLanes The number of new lanes to create.
     * @param area An area below the row being resized.
     * @return The resized road.
     */
    public ArrayList<GraphLane> resizeRow(ArrayList<GraphLane> road, int level,
            int newLanes, GenericContainerEntry containerEntry) {

        int laneHeight = road.get(0).getSize();
        int bump = laneHeight * newLanes;
        int lowerBound = road.get(road.size() - 1).getPosY() - laneHeight;

        road.addAll(road.size(), generateUpperRowLanes(lowerBound, bump, laneHeight, containerEntry));
        containerEntry.getLevelParent().setRowAbove(road);

        bumpDownContainersAt(level, bump);

        graph.redrawEdges();

        return road;
    }

    /**
     * Moves down all levels starting at the given level and not bumping down the row above it.
     * @param level The level to start at
     * @param bump The amount in pixels to move everything down
     */
    private void bumpDownContainersAt(int level, int bump) {
        ArrayList<JPanel> aBelow = new ArrayList<JPanel>();

        for (int n = level; n < levels.size(); n++) {
            GraphLevel l = levels.get(n);

            GenericContainerEntry a;

            for (int i = 0; i < l.getContainerEntries().size(); i++) {
                a = l.getContainerEntries().get(i);
                aBelow.add(a);
            }

        }

        moveBoxes(aBelow, 0, bump);
    }

    public void resizePartitionEntry(GenericPartitionEntry partition, int rows, int cols,
            ArrayList<GenericGroupEntry> groups) {

        int oldWidth = partition.getWidth();
        
        int newWidth = cols * (GenericGroupEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH) + GraphLayoutConstants.GROUP_CHANNEL_WIDTH;
        
        JLabel oldPartitionLabel = partition.getLabel();

        graph.getLabelManager().removeLabel(oldPartitionLabel);
        
        JLabel partitionLabel = this.createPartitionLabel(partition.getPartition(), partition.getWidth());
        
        partition.setLabel(partitionLabel);
        
        newWidth = Math.max(newWidth, partitionLabel.getWidth() + 8) ;
        
        partitionLabel.setBounds(
                (newWidth - partitionLabel.getWidth()) / 2, 
                oldPartitionLabel.getY(), 
                partitionLabel.getWidth(), 
                partitionLabel.getHeight());
        
        int newHeight = 
                partitionLabel.getHeight() + 
                rows * (GenericGroupEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT) + 
                GraphLayoutConstants.GROUP_ROW_HEIGHT + 8;
        
        partition.setBounds(partition.getX(),
            partition.getY(),
            newWidth,
            newHeight);
        
        int pIndex = 0;
        partition.clearGroupLevels();
        partition.clearColumns();
        partition.getParentContainer().clearRows();

        for (int y = 0; y < rows; y++) {
            GraphGroupLevel currentLevel = new GraphGroupLevel(y, partition);

            partition.addGroupLevel(currentLevel);
            partition.getParentContainer().addRow(y, 
                    generateUpperRowLanes(-4, GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, partition.getParentContainer()));

            for (int x = 0; x < cols; x++) {
                if (pIndex < groups.size()) {
                    if (y == 0) {
                        partition.addColumn(x, 
                                generateColumnLanes(-3, GraphLayoutConstants.GROUP_CHANNEL_WIDTH - 2, 3,
                                partition.getParentContainer()));
                    }

                    GenericGroupEntry p = groups.get(pIndex);

                    p.setBounds(x * (GenericGroupEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH) +
                            GraphLayoutConstants.GROUP_CHANNEL_WIDTH,
                            y * (GenericGroupEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT) +
                            GraphLayoutConstants.GROUP_ROW_HEIGHT + 30,
                            GenericGroupEntry.ENTRY_WIDTH,
                            GenericGroupEntry.ENTRY_HEIGHT);

                    currentLevel.addGroupEntry(p);

                    p.setGroupX(x);
                    p.setGroupLevelParent(currentLevel);

                    pIndex++;
                } else {
                    break;
                }
            }
        }

        GenericContainerEntry parent = partition.getParentContainer();

        int height = 0;

        for (int i = 0; i < parent.getContainerPartitions().size(); i++) {

            GenericPartitionEntry r = parent.getContainerPartitions().get(i);

            if (i > parent.getContainerPartitions().indexOf(partition)) {
                r.setBounds(r.getX() + partition.getWidth() - oldWidth, r.getY(), r.getWidth(), r.getHeight());
            }

            if (r.getHeight() > height) {
                height = r.getHeight();
            }
        }

        height += GraphLayoutConstants.GROUP_ROW_HEIGHT + 10;

        int oldHeight = parent.getParentLevel().getHeight();

        parent.setBounds(parent.getX(), parent.getY(), parent.getWidth() + partition.getWidth() - oldWidth, height);
        parent.cascadeExpandToRight(partition.getWidth() - oldWidth);

        int heightChange = parent.getParentLevel().getHeight() - oldHeight;

        for (int i = levels.indexOf((parent.getParentLevel())) + 1; i < levels.size(); i++) {
            GraphLevel l = levels.get(i);

            for (GenericContainerEntry a : l.getContainerEntries()) {
                a.setBounds(a.getX(), a.getY() + heightChange, a.getWidth(), a.getHeight());
            }
        }

        graph.redrawEdges();
    }

    /**
     * Safely moves a JPanel within a graph - checking to see if it exceeds the current boundaries and, if so, adjusting accordingly.
     * @param j The JPanel to move.
     * @param x The amount to move the JPanel horizontally.
     * @param y The amount to move the JPanel vertically.
     */
    public void moveBox(JPanel j, int x, int y) {
        if (j != null) {
            j.setLocation(j.getX() + x, j.getY() + y);

            if (j.getX() + j.getWidth() > graph.getWidth() - 40) {
                graph.setGraphWidth(j.getX() + j.getWidth() + 40);
                graph.updateSize();
            }

            if (j.getY() + j.getHeight() > graph.getHeight() - 40) {
                graph.setGraphHeight(j.getY() + j.getHeight() + 40);
                graph.updateSize();
            }
        }
    }

    /**
     * Safely moves an array of JPanels using the moveBox method.
     * @param list The JPanels to move.
     * @param x The amount to move the JPanel horizontally.
     * @param y The amount to move the JPanel vertically.
     */
    public void moveBoxes(JPanel[] list, int x, int y) {
        for (int n = 0; n < list.length; n++) {
            JPanel j = list[n];
            moveBox(j, x, y);
        }
    }

    /**
     * Safely moves an arraylist of JPanels using the moveBox method.
     * @param list The JPanels to move.
     * @param x The amount to move the JPanel horizontally.
     * @param y The amount to move the JPanel vertically.
     */
    public void moveBoxes(ArrayList<JPanel> list, int x, int y) {
        for (JPanel j : list) {
            j.setLocation(j.getX() + x, j.getY() + y);
            moveBox(j, x, y);
        }
    }


     /**
     * Safely resizes a JPanel checking to make sure the new size doesn't exceed the current bounds of the graph, and resizing it if it does.
     * @param box
     * @param width
     * @param height
     */
    public void resizeBox(JPanel box, int width, int height) {
        box.setSize(width, height);

        if (box.getComponent(0) instanceof JLabel) {
            box.getComponent(0).setBounds(10, 2, width - 15, 25);
        }

        if (box.getX() + box.getWidth() > graph.getWidth() - 40) {

            graph.setGraphWidth(box.getX() + box.getWidth() + 40);
            graph.updateSize();
        }

        if (box.getY() + box.getHeight() > graph.getHeight() - 40) {
            graph.setGraphHeight(box.getY() + box.getHeight() + 40);
            graph.updateSize();
        }
    }

    protected void centerGraphLevels(ArrayList<GraphLevel> graphLevels) {        
        int maxWidth = graphLevels.get(0).getWidth();
        
        for(int c = 1; c < graphLevels.size(); c++) {
            int levelWidth = graphLevels.get(c).getWidth();
            
            if(levelWidth > maxWidth) {
                maxWidth = levelWidth;
            }
        }
        
        for(GraphLevel level : graphLevels) {
            
            int levelOffset = (maxWidth - level.getWidth()) / 2;
            
            for(GenericContainerEntry entry : level.getContainerEntries()) {
                Point location = entry.getLocation();
                location.x += levelOffset;
                
                entry.setLocation(location);
            }
        }
    }
    
    protected JLabel createFittedPartitionLabel(String[] entries, int boundingWidth, FontMetrics fontMetrics) {
       return GraphLayoutUtilities.createFittedPartitionLabel(entries, boundingWidth, fontMetrics);
    }
}
