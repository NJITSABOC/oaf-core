package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Details about which singly rooted node(s) a concept is summarized by
 * within a partitioned node (e.g., an overlapping concept summarized by multiple partial-areas)
 * 
 * @author Chris O
 */
public class OverlappingConceptDetails {
    private final Concept concept;
    private final Set<? extends SinglyRootedNode> nodes;
    
    public OverlappingConceptDetails(Concept concept, Set<? extends SinglyRootedNode> nodes) {
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
