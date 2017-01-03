package edu.njit.cs.saboc.blu.core.graph.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.AbstractionNetworkGraphLayout;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.EmptyContainerEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.EmptyContainerPartitionEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public class GenericDisjointAbNLayout<T extends DisjointAbstractionNetwork> extends AbstractionNetworkGraphLayout<T> {

    private final DisjointAbstractionNetwork disjointAbN;

    public GenericDisjointAbNLayout(AbstractionNetworkGraph<T> graph, DisjointAbstractionNetwork disjointAbN) {
        super(graph);

        this.disjointAbN = disjointAbN;
    }
    
    public DisjointAbstractionNetwork getDisjointAbN() {
        return disjointAbN;
    }

    public void doLayout() {
        
        Set<DisjointNode> disjointNodes = disjointAbN.getAllDisjointNodes();
                
        ArrayList<DisjointNode> nonoverlappingNodes = new ArrayList<>();
        
        disjointNodes.forEach((group) -> {
            if(group.getOverlaps().size() == 1) {
                nonoverlappingNodes.add(group);
            }
        });
        
        Color[] colors = this.createOverlapColors();

        HashMap<SinglyRootedNode, Color> colorMap = new HashMap<>();
        
        int colorId = 0;

        for (DisjointNode dpa : nonoverlappingNodes) {
            SinglyRootedNode overlapGroup = (SinglyRootedNode)dpa.getOverlaps().iterator().next();

            if (colorId >= colors.length) {
                colorMap.put(overlapGroup, Color.gray);
            } else {
                colorMap.put(overlapGroup, colors[colorId]);
                colorId++;
            }
        }
        
        ArrayList<ArrayList<DisjointNode>> nodeLevels = new ArrayList<>();
        
         for (int overlapSize = 1; overlapSize <= disjointAbN.getLevelCount(); overlapSize++) {
            ArrayList<DisjointNode> levelNodes = new ArrayList<>();

            for (DisjointNode disjointGroup : disjointNodes) {
                if (disjointGroup.getOverlaps().size() == overlapSize) {
                    levelNodes.add(disjointGroup);
                }
            }

            if (overlapSize > 1) {
                levelNodes = disjointPAreaSort(levelNodes);
            } else {
                Collections.sort(levelNodes, (a, b) -> b.getConceptCount() - a.getConceptCount());
            }
            
            nodeLevels.add(levelNodes);
        }
        
        int containerX = 0;
        int containerY = 0; 
        int y = 20;
        int x = 0;
        int maxRowNodes = 14;
        int widthPadding = 40;
        int heightPadding = 60; 

        addGraphLevel(new GraphLevel(0, getGraph(), new ArrayList<>())); 

        for (ArrayList<DisjointNode> nodeLevel : nodeLevels) {
            int width = 0;

            int nodeCount = nodeLevel.size();

            int nodeEntriesWide = Math.min(maxRowNodes, nodeCount);

            int levelWidth = nodeEntriesWide * (DisjointNodeEntry.DISJOINT_NODE_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);

            width += levelWidth + widthPadding;

            int height = (int) (Math.ceil((double) nodeCount / nodeEntriesWide))
                    * (DisjointNodeEntry.DISJOINT_NODE_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT);

            height += heightPadding + GraphLayoutConstants.GROUP_ROW_HEIGHT;

            GraphLevel currentLevel = getLevels().get(containerY);

            EmptyContainerEntry containerEntry = createContainerPanel(x, y, width, height, containerX, currentLevel);
            
            PartitionedNode dummyNode = new PartitionedNode(new HashSet<>()) {
                @Override
                public String getName(String separator) {
                    return "NULL";
                }

                @Override
                public String getName() {
                    return "NULL";
                }

                @Override
                public boolean equals(Object o) {
                    return this == o;
                }

                @Override
                public int hashCode() {
                    return super.getInternalNodes().hashCode();
                }
                
            };

            getContainerEntries().put(dummyNode, containerEntry);

            // Add a data representation for this new area to the current area Level obj.
            currentLevel.addContainerEntry(containerEntry);

            // Generates a column of lanes to the left of this area.
            addColumn(containerX, currentLevel.getLevelY(), generateColumnLanes(-3,
                    GraphLayoutConstants.CONTAINER_CHANNEL_WIDTH - 5, 3, null));

            int [] groupX = new int[(int)Math.ceil((double)nodeLevel.size() / (double)nodeEntriesWide)];

            int x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
            int y2 = 30;

            int disjointGroupX = 0;
            int disjointGroupY = 0;

            EmptyContainerPartitionEntry currentPartition = createPartitionPanel(containerEntry, 0, 0, width, height);
            
            containerEntry.addPartitionEntry(currentPartition);

            currentPartition.addGroupLevel(new GraphGroupLevel(0, currentPartition)); // Add a new pAreaLevel to the data representation of the current Area object.

            containerEntry.addRow(0, generateUpperRowLanes(-4,
                    GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, containerEntry));

            int nodeIndex = 0;

            for (DisjointNode group : nodeLevel) {
                
                GraphGroupLevel currentClusterLevel = currentPartition.getGroupLevels().get(disjointGroupY);
                
                DisjointNodeEntry targetGroupEntry = createGroupPanel(group, currentPartition, x2, y2, disjointGroupX, currentClusterLevel, colorMap);

                currentPartition.getVisibleGroups().add(targetGroupEntry);

                currentPartition.addColumn(groupX[disjointGroupY], generateColumnLanes(-3,
                        GraphLayoutConstants.GROUP_CHANNEL_WIDTH - 2, 3, containerEntry));

                getGroupEntries().put(group, targetGroupEntry);    // Store it in a map keyed by its ID...

                currentClusterLevel.addGroupEntry(targetGroupEntry);

                if ((nodeIndex + 1) % nodeEntriesWide == 0 && nodeIndex < nodeLevel.size() - 1) {
                    y2 += DisjointNodeEntry.DISJOINT_NODE_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT;
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
                    x2 += (DisjointNodeEntry.DISJOINT_NODE_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    disjointGroupX++;
                    groupX[disjointGroupY]++;
                }

                nodeIndex++;
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
        
    private ArrayList<DisjointNode> disjointPAreaSort(ArrayList<DisjointNode> entries) {
        
        Map<Set<Node>, ArrayList<DisjointNode>> overlapsMap = new HashMap<>();
        
        entries.forEach( (entry) -> {
            Set<Node> overlaps = entry.getOverlaps();
            
            if(!overlapsMap.containsKey(entry.getOverlaps())) {
                overlapsMap.put(overlaps, new ArrayList<>());
            }
            
            overlapsMap.get(overlaps).add(entry);
        });
                
        ArrayList<ArrayList<DisjointNode>> sortedDisjointNodes = new ArrayList<>();
        
        overlapsMap.values().forEach( (disjointNodes) -> {
            
            disjointNodes.sort( (a, b) -> {
                if(a.getConceptCount() == b.getConceptCount()) {
                    return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
                } else {
                    return b.getConceptCount() - a.getConceptCount();
                }
            });
            
            sortedDisjointNodes.add(disjointNodes);
        });
        
        sortedDisjointNodes.sort( (a, b) -> {
            return b.size() - a.size();
        });

        ArrayList<DisjointNode> finalSortedEntries = new ArrayList<>();
        sortedDisjointNodes.forEach( (list) -> {
            finalSortedEntries.addAll(list);
        });
        
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

        Collections.sort(groups, (a, b) -> b.getConceptCount() - a.getConceptCount());

        Color[] dpaColors = new Color[groups.size()];

        for (int c = 0; c < dpaColors.length; c++) {
            dpaColors[c] = colorMap.get(groups.get(c));
        }

        DisjointNodeEntry targetGroupEntry = new DisjointNodeEntry(node, getGraph(), parent, groupX, groupLevel, new ArrayList<>(), dpaColors);
        
        targetGroupEntry = (DisjointNodeEntry)targetGroupEntry.labelOffset(new Point(DisjointNodeEntry.DISJOINT_LABEL_OFFSET, DisjointNodeEntry.DISJOINT_LABEL_OFFSET));

        //Make sure this panel dimensions will fit on the graph, stretch the graph if necessary
        getGraph().stretchGraphToFitPanel(x, y, DisjointNodeEntry.DISJOINT_NODE_WIDTH, DisjointNodeEntry.DISJOINT_NODE_HEIGHT);

        //Setup the panel's dimensions, etc.
        targetGroupEntry.setBounds(x, y,  SinglyRootedNodeEntry.ENTRY_WIDTH,  SinglyRootedNodeEntry.ENTRY_HEIGHT);

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
