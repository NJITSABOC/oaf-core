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
                fromOntology.getConceptHierarchy().getNodes(), 
                toOntology.getConceptHierarchy().getNodes());
        
        // Concepts that were added to the ontology
        Set<Concept> addedOntConcepts = SetUtilities.getSetDifference(
                toOntology.getConceptHierarchy().getNodes(), 
                fromOntology.getConceptHierarchy().getNodes());
        
        // Concepts that were removed from the hierarchy
        Set<Concept> removedHierarchyConcepts = SetUtilities.getSetDifference(
                fromSubhierarchy.getNodes(), 
                toSubhierarchy.getNodes());
        
        // Concepts that were added to the hierarchy
        Set<Concept> addedHierarchyConcepts = SetUtilities.getSetDifference(
                toSubhierarchy.getNodes(), 
                fromSubhierarchy.getNodes());
        
        Set<Concept> transferredHierarchyConcepts = SetUtilities.getSetIntersection(
                fromSubhierarchy.getNodes(), 
                toSubhierarchy.getNodes());
        
        
        transferredHierarchyConcepts.forEach( (concept) -> {
            Set<Concept> fromParents = fromSubhierarchy.getParents(concept);
            Set<Concept> toParents = toSubhierarchy.getParents(concept);
            
            Set<Concept> removedParents = SetUtilities.getSetDifference(fromParents, toParents);
            Set<Concept> addedParents = SetUtilities.getSetDifference(toParents, fromParents);
            Set<Concept> transferredParents = SetUtilities.getSetIntersection(toParents, fromParents);
            
            
            
        });
        
        
    }
    
}
