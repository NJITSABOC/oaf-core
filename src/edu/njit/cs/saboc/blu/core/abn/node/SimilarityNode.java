package edu.njit.cs.saboc.blu.core.abn.node;

import java.util.Set;

/**
 * Represents an abstraction network node that captures similarity of some kind, 
 * for example, structural similarity for an area.
 * 
 * @author Chris O
 */
public abstract class SimilarityNode<T extends SinglyRootedNode> extends PartitionedNode {
    
    public SimilarityNode(Set<T> internalNodes) {
        super(internalNodes);
    }
}
