package edu.njit.cs.saboc.blu.core.ontology;

/**
 *
 * @author Chris O
 */
public class Ontology {
    private final SingleRootedConceptHierarchy conceptHierarchy;
    
    public Ontology(SingleRootedConceptHierarchy conceptHierarchy) {
        this.conceptHierarchy = conceptHierarchy;
    }
    
    public SingleRootedConceptHierarchy getConceptHierarchy() {
        return conceptHierarchy;
    }
}
