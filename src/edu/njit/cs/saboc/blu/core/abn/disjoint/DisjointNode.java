package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Chris
 * @param <T> The type of node that this 
 */
public abstract class DisjointNode<T extends Node> extends SinglyRootedNode {
    
    private final Set<T> overlapsIn;

    public DisjointNode(
            ConceptHierarchy conceptHierarchy, 
            Set<T> overlapsIn) {
        
        super(conceptHierarchy);
        
        this.overlapsIn = overlapsIn;
    }
        
    /**
     * Returns which partial-areas the concepts of this disjoint partial-area overlap between
     * @return 
     */
    public Set<T> getOverlaps() {
        return overlapsIn;
    }
    
}
