package edu.njit.cs.saboc.blu.core.graph.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.BluGraphLayout;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.EmptyContainerEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.EmptyContainerPartitionEntry;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public class GenericDisjointAbNLayout extends BluGraphLayout {

    private final DisjointAbstractionNetwork disjointAbN;

    public GenericDisjointAbNLayout(BluGraph graph, DisjointAbstractionNetwork disjointAbN) {
        super(graph);

        this.disjointAbN = disjointAbN;
    }
    
    public DisjointAbstractionNetwork getDisjointAbN() {
        return disjointAbN;
    }

    public void doLayout() {
        
        Set<DisjointNode> disjointGroups = disjointAbN.getAllDisjointNodes();
                
        ArrayList<DisjointNode> nonoverlappingGroups = new ArrayList<>();
        
        disjointGroups.forEach( (group) -> {
            if(group.getOverlaps().size() == 1) {
                nonoverlappingGroups.add(group);
            }
        });
        
        Color[] colors = this.createOverlapColors();

        HashMap<SinglyRootedNode, Color> colorMap = new HashMap<>();
        
        int colorId = 0;

        for (DisjointNode dpa : nonoverlappingGroups) {
            SinglyRootedNode overlapGroup = (SinglyRootedNode)dpa.getOverlaps().iterator().next();

            if (colorId >= colors.length) {
                colorMap.put(overlapGroup, Color.gray);
            } else {
                colorMap.put(overlapGroup, colors[colorId]);
                colorId++;
            }
        }
        
        ArrayList<ArrayList<DisjointNode>> groupLevels = new ArrayList<>();
        
         for (int overlapSize = 1; overlapSize <= disjointAbN.getLevelCount(); overlapSize++) {
            ArrayList<DisjointNode> levelGroups = new ArrayList<>();

            for (DisjointNode disjointGroup : disjointGroups) {
                if (disjointGroup.getOverlaps().size() == overlapSize) {
                    levelGroups.add(disjointGroup);
                }
            }

            Collections.sort(levelGroups, new Comparator<DisjointNode>() {
                public int compare(DisjointNode a, DisjointNode b) {
                    return b.getConceptCount() - a.getConceptCount();
                }
            });

            if (overlapSize > 1) {
                levelGroups = disjointPAreaSort(levelGroups, nonoverlappingGroups, 0, disjointAbN.getLevelCount());
            }

            groupLevels.add(levelGroups);
        }
        
        int containerX = 0;  // The first area on each line is given an areaX value of 0.
        int containerY = 0;  // The first row of areas is given an areaY value of 0.
        int x = 0;
        int y = 20;
        
        int areaId = 0;

        addGraphLevel(new GraphLevel(0, getGraph(), new ArrayList<>())); // Add the first level of areas (the single pArea 0-relationship level) to the data representation of the graph.

        for (ArrayList<DisjointNode> groupLevel : groupLevels) {  // Loop through the areas and generate the diagram for each of them
            int width = 0;

            int groupCount = groupLevel.size();

            int groupEntriesWide = Math.min(14, groupCount);

            int levelWidth = groupEntriesWide * (DisjointNodeEntry.DISJOINT_GROUP_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);

            width += levelWidth + 20;

            int height = (int) (Math.ceil((double) groupCount / groupEntriesWide))
                    * (DisjointNodeEntry.DISJOINT_GROUP_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT);

            width += 20;
            height += 60 + GraphLayoutConstants.GROUP_ROW_HEIGHT;

            GraphLevel currentLevel = getLevels().get(containerY);

            EmptyContainerEntry containerEntry = createContainerPanel(x, y, width, height, containerX, currentLevel);

            getContainerEntries().put(areaId++, containerEntry);

            // Add a data representation for this new area to the current area Level obj.
            currentLevel.addContainerEntry(containerEntry);

            // Generates a column of lanes to the left of this area.
            addColumn(containerX, currentLevel.getLevelY(), generateColumnLanes(-3,
                    GraphLayoutConstants.CONTAINER_CHANNEL_WIDTH - 5, 3, null));

            int [] groupX = new int[(int)Math.ceil((double)groupLevel.size() / (double)groupEntriesWide)];

            int x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
            int y2 = 30;

            int disjointGroupX = 0;
            int disjointGroupY = 0;

            EmptyContainerPartitionEntry currentPartition = createPartitionPanel(containerEntry, 0, 0, width, height);
            
            containerEntry.addPartitionEntry(currentPartition);

            currentPartition.addGroupLevel(new GraphGroupLevel(0, currentPartition)); // Add a new pAreaLevel to the data representation of the current Area object.

            containerEntry.addRow(0, generateUpperRowLanes(-4,
                    GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, containerEntry));

            int i = 0;

            for (DisjointNode group : groupLevel) {
                
                GraphGroupLevel currentClusterLevel = currentPartition.getGroupLevels().get(disjointGroupY);
                
                DisjointNodeEntry targetGroupEntry = createGroupPanel(group, currentPartition, x2, y2, disjointGroupX, currentClusterLevel, colorMap);

                currentPartition.getVisibleGroups().add(targetGroupEntry);

                currentPartition.addColumn(groupX[disjointGroupY], generateColumnLanes(-3,
                        GraphLayoutConstants.GROUP_CHANNEL_WIDTH - 2, 3, containerEntry));

                getGroupEntries().put(group, targetGroupEntry);    // Store it in a map keyed by its ID...

                currentClusterLevel.addGroupEntry(targetGroupEntry);

                if ((i + 1) % groupEntriesWide == 0 && i < groupLevel.size() - 1) {
                    y2 += DisjointNodeEntry.DISJOINT_GROUP_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT;
                    x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    disjointGroupX = 0;
                    
                    groupX[disjointGroupY]++;
                    
                    disjointGroupY++;

                    if (currentPartition.getGroupLevels().size() <= disjointGroupY) {
                        currentPartition.addGroupLevel(new GraphGroupLevel(disjointGroupY, currentPartition)); // Add a new pAreaLevel to the data representation of the current Area object.
                        
                        containerEntry.addRow(disjointGroupY, generateUpperRowLanes(-4,
                                GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, containerEntry));
                    }
                } else {
                    x2 += (DisjointNodeEntry.DISJOINT_GROUP_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    disjointGroupX++;
                    groupX[disjointGroupY]++;
                }

                i++;
            }

            x = 0;  // Reset the x coordinate to the left
            y += height + GraphLayoutConstants.CONTAINER_ROW_HEIGHT;

            containerY++;    // Update the areaY variable to reflect the new row.
            containerX = 0;  // Reset the areaX variable.

            addGraphLevel(new GraphLevel(
                    containerY, 
                    getGraph(),
                    generateUpperRowLanes(-5, GraphLayoutConstants.CONTAINER_ROW_HEIGHT - 7, 3, null))); // Add a level object to 
        }
        
        this.centerGraphLevels(this.getGraphLevels());
    }
    
    private Color[] createOverlapColors() {

        int iterations = 4;

        Color[] seedColors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.CYAN, Color.LIGHT_GRAY, Color.MAGENTA};

        Color[] colors = new Color[seedColors.length * iterations];

        int index = 0;

        for (index = 0; index < seedColors.length; index++) {
            colors[index] = seedColors[index];
        }

        double scale = 0.8;

        for (int c = 0; c < iterations - 1; c++) {
            for (Color color : seedColors) {
                int r = (int) (color.getRed() * scale);
                int g = (int) (color.getGreen() * scale);
                int b = (int) (color.getBlue() * scale);

                colors[index++] = new Color(r, g, b);
            }

            scale -= 0.2;
        }

        return colors;
    }
        
    private ArrayList<DisjointNode> disjointPAreaSort(ArrayList<DisjointNode> entries, ArrayList<DisjointNode> topLevel, int currentLevel, int maxLevel) {
        
        if(currentLevel >= maxLevel) {
            return entries;
        }

        ArrayList<ArrayList<DisjointNode>> sortedEntries = new ArrayList<>();

        HashSet<DisjointNode> processed = new HashSet<>();

        for (int c = currentLevel; c < topLevel.size(); c++) {
            SinglyRootedNode overlap = (SinglyRootedNode)topLevel.get(c).getOverlaps().iterator().next();

            ArrayList<DisjointNode> sorted = new ArrayList<>();

            for (DisjointNode entry : entries) {
                if (!processed.contains(entry)) {
                    if (entry.getOverlaps().contains(overlap)) {
                        sorted.add(entry);
                        processed.add(entry);
                    }
                }
            }

            sorted = disjointPAreaSort(sorted, topLevel, c + 1, maxLevel);

            sortedEntries.add(sorted);
        }

        ArrayList<DisjointNode> finalSortedEntries = new ArrayList<>();

        for (ArrayList<DisjointNode> entry : sortedEntries) {
            finalSortedEntries.addAll(entry);
        }

        return finalSortedEntries;
    }

    private DisjointNodeEntry createGroupPanel(DisjointNode node, 
             EmptyContainerPartitionEntry parent, 
             int x, 
             int y,
             int groupX, 
             GraphGroupLevel groupLevel, 
             HashMap<SinglyRootedNode, Color> colorMap) {
         
        ArrayList<SinglyRootedNode> groups = new ArrayList<>(node.getOverlaps());

        Collections.sort(groups, new Comparator<SinglyRootedNode>() {
            public int compare(SinglyRootedNode a, SinglyRootedNode b) {
                return b.getConceptCount() - a.getConceptCount();
            }
        });

        Color[] dpaColors = new Color[groups.size()];

        for (int c = 0; c < dpaColors.length; c++) {
            dpaColors[c] = colorMap.get(groups.get(c));
        }

        DisjointNodeEntry targetGroupEntry = new DisjointNodeEntry(node, getGraph(), parent, groupX, groupLevel, new ArrayList<>(), dpaColors);
        
        targetGroupEntry = (DisjointNodeEntry)targetGroupEntry.labelOffset(new Point(DisjointNodeEntry.DISJOINT_LABEL_OFFSET, DisjointNodeEntry.DISJOINT_LABEL_OFFSET));

        //Make sure this panel dimensions will fit on the graph, stretch the graph if necessary
        getGraph().stretchGraphToFitPanel(x, y, DisjointNodeEntry.DISJOINT_GROUP_WIDTH, DisjointNodeEntry.DISJOINT_GROUP_HEIGHT);

        //Setup the panel's dimensions, etc.
        targetGroupEntry.setBounds(x, y,DisjointNodeEntry.DISJOINT_GROUP_WIDTH, DisjointNodeEntry.DISJOINT_GROUP_HEIGHT);

        parent.add(targetGroupEntry, 0);

        return targetGroupEntry;
    }

    protected EmptyContainerEntry createContainerPanel(int x, int y, int width, int height, int areaX, GraphLevel parentLevel) {
        EmptyContainerEntry targetPanel = new EmptyContainerEntry(getGraph(), areaX, parentLevel, new Rectangle(x, y, width, height));

        getGraph().stretchGraphToFitPanel(x, y, width, height);

        targetPanel.setBounds(x, y, width, height);

        getGraph().add(targetPanel, 0);

        return targetPanel;
    }

    protected EmptyContainerPartitionEntry createPartitionPanel(EmptyContainerEntry container, int x, int y, int width, int height) {
        EmptyContainerPartitionEntry partitionPanel = new EmptyContainerPartitionEntry(width, height, container, getGraph());

        getGraph().stretchGraphToFitPanel(x, y, width, height);

        partitionPanel.setBounds(x, y, width, height);

        container.add(partitionPanel, 0);

        return partitionPanel;
    }
    
    @Override
    public JLabel createPartitionLabel(PartitionedNode partition, int width) {
        return new JLabel();
    }
    
}
