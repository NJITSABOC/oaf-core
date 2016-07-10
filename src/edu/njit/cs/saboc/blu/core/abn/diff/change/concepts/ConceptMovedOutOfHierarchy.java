package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris
 */
public class ConceptMovedOutOfHierarchy extends NodeConceptChange {
    public ConceptMovedOutOfHierarchy(Node node, Concept concept) {
        super(node, concept);
    }
}
