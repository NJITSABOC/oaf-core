package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris
 */
public class ConceptAddedToOntology extends NodeConceptChange {

    public ConceptAddedToOntology(Node node, Concept concept) {
        super(node, concept);
    }
}
