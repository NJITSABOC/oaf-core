package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OntologyDifferences {
    private final Set<Concept> removedConcepts;
    private final Set<Concept> addedConcepts;
    
    public OntologyDifferences(Set<Concept> removedConcepts, Set<Concept> addedConcepts) {
        this.removedConcepts = removedConcepts;
        this.addedConcepts = addedConcepts;
    }
    
    public Set<Concept> getRemovedConcepts() {
        return removedConcepts;
    }
    
    public Set<Concept> getAddedConcepts() {
        return addedConcepts;
    }
}
