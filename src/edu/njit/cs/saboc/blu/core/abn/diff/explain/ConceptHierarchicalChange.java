package edu.njit.cs.saboc.blu.core.abn.diff.explain;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class ConceptHierarchicalChange extends DiffAbNConceptChange {

    public ConceptHierarchicalChange(
            Concept affectedConcept,
            ChangeInheritanceType changeInheritance) {
        
        super(affectedConcept, changeInheritance);
    }
}
