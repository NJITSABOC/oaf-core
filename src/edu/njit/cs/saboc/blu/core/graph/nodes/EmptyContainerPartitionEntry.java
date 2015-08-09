package edu.njit.cs.saboc.blu.core.graph.nodes;

import edu.njit.cs.saboc.blu.core.graph.BluGraph;

/**
 *
 * @author Chris O
 */
public class EmptyContainerPartitionEntry extends GenericPartitionEntry {
    public EmptyContainerPartitionEntry(int width, int height, GenericContainerEntry parent, BluGraph g) {
        super(null, "", width, height, g, parent, null, true);
    }
}
