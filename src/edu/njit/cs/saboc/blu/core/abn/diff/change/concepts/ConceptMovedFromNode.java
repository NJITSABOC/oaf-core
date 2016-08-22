package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a concept moving FROM a different node TO the node
 * @author Chris
 */
public class ConceptMovedFromNode extends NodeConceptChange {
    
    private final Node from;
    
    public ConceptMovedFromNode(Node to, Node from, Concept concept) {
        super(NodeConceptSetChangeType.MovedFromNode, to, concept);
        
        this.from = from;
    }
    
    public Node getMovedFrom() {
        return from;
    }
}
