package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a concept moving FROM the given node TO a different node
 * @author Chris
 */
public class ConceptMovedToNode extends NodeConceptChange {
    private final Node to;
    
    public ConceptMovedToNode(Node from, Node to, Concept concept) {
        super(from, concept);
        
        this.to = to;
    }
    
    public Node getMovedTo() {
        return to;
    }
}
