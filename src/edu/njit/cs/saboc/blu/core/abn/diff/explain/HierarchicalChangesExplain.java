package edu.njit.cs.saboc.blu.core.abn.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class HierarchicalChangesExplain {

    public HierarchicalChangesExplain(
            Ontology fromOntology,
            Hierarchy<Concept> fromSubhierarchy, 
            Ontology toOntology,
            Hierarchy<Concept> toSubhierarchy) {
        
        
        // Concepts that were removed from the ontology
        Set<Concept> removedOntConcepts = SetUtilities.getSetDifference(
                fromOntology.getConceptHierarchy().getNodesInHierarchy(), 
                toOntology.getConceptHierarchy().getNodesInHierarchy());
        
        // Concepts that were added to the ontology
        Set<Concept> addedOntConcepts = SetUtilities.getSetDifference(
                toOntology.getConceptHierarchy().getNodesInHierarchy(), 
                fromOntology.getConceptHierarchy().getNodesInHierarchy());
        
        // Concepts that were removed from the hierarchy
        Set<Concept> removedHierarchyConcepts = SetUtilities.getSetDifference(
                fromSubhierarchy.getNodesInHierarchy(), 
                toSubhierarchy.getNodesInHierarchy());
        
        // Concepts that were added to the hierarchy
        Set<Concept> addedHierarchyConcepts = SetUtilities.getSetDifference(
                toSubhierarchy.getNodesInHierarchy(), 
                fromSubhierarchy.getNodesInHierarchy());
        
        Set<Concept> transferredHierarchyConcepts = SetUtilities.getSetIntersection(
                fromSubhierarchy.getNodesInHierarchy(), 
                toSubhierarchy.getNodesInHierarchy());
        
        
        transferredHierarchyConcepts.forEach( (concept) -> {
            Set<Concept> fromParents = fromSubhierarchy.getParents(concept);
            Set<Concept> toParents = toSubhierarchy.getParents(concept);
            
            Set<Concept> removedParents = SetUtilities.getSetDifference(fromParents, toParents);
            Set<Concept> addedParents = SetUtilities.getSetDifference(toParents, fromParents);
            Set<Concept> transferredParents = SetUtilities.getSetIntersection(toParents, fromParents);
            
            
            
        });
        
        
    }
    
}
