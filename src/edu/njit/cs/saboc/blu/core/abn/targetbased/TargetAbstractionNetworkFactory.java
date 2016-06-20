package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.SingleRootedConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public interface TargetAbstractionNetworkFactory {
    public Set<RelationshipTriple> getRelationshipsToTargetHierarchyFor(
            Concept concept, 
            Set<InheritableProperty> relationshipTypes, 
            SingleRootedConceptHierarchy targetHierarchy);
}
