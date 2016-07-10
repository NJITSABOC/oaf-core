package edu.njit.cs.saboc.blu.core.ontology;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris O
 */
public class Ontology<T extends Concept> {
    private final Hierarchy<T> conceptHierarchy;
    
    public Ontology(Hierarchy<T> conceptHierarchy) {
        this.conceptHierarchy = conceptHierarchy;
    }
    
    public Hierarchy<T> getConceptHierarchy() {
        return conceptHierarchy;
    }
}
