package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Represents a concept that is summarized by multiple singly rooted nodes 
 * within a partitioned node (e.g., a concept summarized by multiple partial-areas)
 * 
 * @author Chris O
 */
public class OverlappingConcept {
    private final Concept concept;
    private final Set<? extends SinglyRootedNode> nodes;
    
    public OverlappingConcept(Concept concept, Set<? extends SinglyRootedNode> nodes) {
        this.concept = concept;
        this.nodes = nodes;
    }
    
    public Concept getConcept() {
        return concept;
    }
    
    public Set<SinglyRootedNode> getNodes() {
        return (Set<SinglyRootedNode>)nodes;
    }
}
