
package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a link between two singly rooted nodes. Specifically, if A is
 * CHILD-OF B then this class represents the link from A's root to the concept
 * in B.
 * 
 * @author cro3
 */
public class ParentNodeDetails {
    private final Concept parentConcept;
    private final Node parentNode;
    
    public ParentNodeDetails(Concept parentConcept, Node parentNode) {
        this.parentConcept = parentConcept;
        this.parentNode = parentNode;
    }
    
    public Concept getParentConcept() {
        return parentConcept;
    }
    
    public Node getParentNode() {
        return parentNode;
    }
}
