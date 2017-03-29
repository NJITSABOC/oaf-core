package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;

/**
 * A factory class for creating/obtaining implementation-specific target
 * abstraction network objects
 * 
 * @author Chris O
 */
public abstract class TargetAbstractionNetworkFactory {
    
    private final Ontology sourceOntology;
    
    public TargetAbstractionNetworkFactory(Ontology sourceOntology) {
        this.sourceOntology = sourceOntology;
    }
    
    public Ontology getSourceOntology() {
        return sourceOntology;
    }
    
    public TargetAbstractionNetwork createTargetAbstractionNetwork(
            Hierarchy<TargetGroup> groupHierarchy, 
            Hierarchy<Concept> sourceHierarchy,
            TargetAbNDerivation derivation) {
        
        return new TargetAbstractionNetwork(
                groupHierarchy, 
                sourceHierarchy,
                derivation);
    }
    
    public abstract Set<RelationshipTriple> getRelationshipsToTargetHierarchyFor(
            Concept concept, 
            Set<InheritableProperty> relationshipTypes, 
            Hierarchy<Concept> targetHierarchy);
}
