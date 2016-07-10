package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris
 */
public class ConceptMovedIntoHierarchy extends NodeConceptChange {
    
    public ConceptMovedIntoHierarchy(Node node, Concept concept) {
        super(node, concept);
    }
}
