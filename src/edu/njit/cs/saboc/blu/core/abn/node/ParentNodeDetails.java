
package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a link between two singly rooted nodes. Specifically, if A is
 * CHILD-OF B then this class represents the link from A's root to the concept
 * in B.
 * 
 * @author cro3
 */
public class ParentConceptNode {
    private final Concept parentConcept;
    private final SinglyRootedNode parentNode;
    
    public ParentConceptNode(Concept parentConcept, SinglyRootedNode parentNode) {
        this.parentConcept = parentConcept;
        this.parentNode = parentNode;
    }
    
    public Concept getParentConcept() {
        return parentConcept;
    }
    
    public SinglyRootedNode getParentNode() {
        return parentNode;
    }
}
