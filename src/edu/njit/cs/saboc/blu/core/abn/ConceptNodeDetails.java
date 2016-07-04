package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class ConceptNodeDetails<NODE_T extends Node> {
    
    private final Concept concept;
    private final Set<NODE_T> nodes;
    
    public ConceptNodeDetails(Concept concept, Set<NODE_T> nodes) {
        this.concept = concept;
        this.nodes = nodes;
    }

    public Concept getConcept() {
        return concept;
    }

    public Set<NODE_T> getNodes() {
        return nodes;
    }
}
