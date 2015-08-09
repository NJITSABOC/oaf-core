package edu.njit.cs.saboc.blu.core.graph.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author cro3
 */
public abstract class BluDisjointGroupEntry<DISJOINTGROUP_T extends DisjointGenericConceptGroup> extends GenericGroupEntry<DISJOINTGROUP_T> {
    
    public static final int DISJOINT_EXTRA_SPACE = 16;
    
    public static final int DISJOINT_LABEL_OFFSET = DISJOINT_EXTRA_SPACE / 2;
    
    public static final int DISJOINT_GROUP_WIDTH = GenericGroupEntry.ENTRY_WIDTH + DISJOINT_EXTRA_SPACE;
    public static final int DISJOINT_GROUP_HEIGHT = GenericGroupEntry.ENTRY_HEIGHT + DISJOINT_EXTRA_SPACE;
    
    private final Color[] colorSet;

    public BluDisjointGroupEntry(DISJOINTGROUP_T group, 
            BluGraph g, 
            GenericPartitionEntry partitionEntry,
            int pX, 
            GraphGroupLevel parent, 
            ArrayList<GraphEdge> ie,
            Color[] colorSet) {
        
        super(group, g, partitionEntry, pX, parent, ie);
        
        this.colorSet = colorSet;
    }
    
    public Color[] getColorSet() {
        return colorSet;
    }
}
