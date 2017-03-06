package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a concept moving FROM one node TO another node between the
 * "FROM" and "TO" versions of the abstraction network.
 * 
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
