package edu.njit.cs.saboc.blu.core.graph.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author cro3
 */
public class DisjointNodeEntry extends SinglyRootedNodeEntry {
    
    public static final int DISJOINT_EXTRA_SPACE = 16;
    
    public static final int DISJOINT_LABEL_OFFSET = DISJOINT_EXTRA_SPACE / 2;
    
    public static final int DISJOINT_NODE_WIDTH = SinglyRootedNodeEntry.ENTRY_WIDTH + DISJOINT_EXTRA_SPACE;
    public static final int DISJOINT_NODE_HEIGHT = SinglyRootedNodeEntry.ENTRY_HEIGHT + DISJOINT_EXTRA_SPACE;
    
    private final Color[] colorSet;

    public DisjointNodeEntry(
            DisjointNode node, 
            BluGraph graph, 
            GenericPartitionEntry partitionEntry,
            int pX, 
            GraphGroupLevel parent, 
            ArrayList<GraphEdge> incomingEdges,
            Color[] colorSet) {
        
        super(node, graph, partitionEntry, pX, parent, incomingEdges);
        
        this.colorSet = colorSet;
    }
    
    public Color[] getColorSet() {
        return colorSet;
    }
}
