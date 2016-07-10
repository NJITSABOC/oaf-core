package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a concept that was removed from an ontology and thus a node's concept set.
 * @author Chris
 */
public class ConceptRemovedFromOntology extends NodeConceptChange {
    
    public ConceptRemovedFromOntology(Node node, Concept concept) {
        super(node, concept);
    }
}
