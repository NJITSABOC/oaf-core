package edu.njit.cs.saboc.blu.core.ontology;

/**
 *
 * @author Chris O
 */
public class Ontology {
    private final ConceptHierarchy conceptHierarchy;
    
    public Ontology(ConceptHierarchy conceptHierarchy) {
        this.conceptHierarchy = conceptHierarchy;
    }
    
    public ConceptHierarchy getConceptHierarchy() {
        return conceptHierarchy;
    }
}
