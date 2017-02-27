package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a concept that was previously in the subhierarchy of concepts
 * summarized by an abstraction network in the "FROM" but no longer being summarized
 * by a node in the "TO" version of the abstraction network. 
 * 
 * Disjoint from the ConceptRemovedFromOntology class.
 * 
 * @author Chris
 */
public class ConceptMovedOutOfHierarchy extends NodeConceptChange {
    public ConceptMovedOutOfHierarchy(Node node, Concept concept) {
        super(NodeConceptSetChangeType.RemovedFromHierarchy, node, concept);
    }
}
