package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a concept that was previously not in the subhierarchy of concepts
 * summarized by an abstraction network in the "FROM" release being summarized
 * by a node in the "TO" release. 
 * 
 * Disjoint from the ConceptAddedToOntology change type.
 * 
 * @author Chris
 */
public class ConceptMovedIntoHierarchy extends NodeConceptChange {
    
    public ConceptMovedIntoHierarchy(Node node, Concept concept) {
        super(NodeConceptSetChangeType.AddedToHierarchy, node, concept);
    }
}
