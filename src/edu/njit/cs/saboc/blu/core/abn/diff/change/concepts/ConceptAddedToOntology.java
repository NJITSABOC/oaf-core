package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a newly-created concept being summarized by a given node
 * in the "TO" version of an abstraction network
 * 
 * @author Chris
 */
public class ConceptAddedToOntology extends NodeConceptChange {

    public ConceptAddedToOntology(Node node, Concept concept) {
        super(NodeConceptSetChangeType.AddedToOnt, node, concept);
    }
}
