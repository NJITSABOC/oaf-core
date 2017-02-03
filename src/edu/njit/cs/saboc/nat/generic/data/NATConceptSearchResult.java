package edu.njit.cs.saboc.nat.generic.data;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

public class NATConceptSearchResult<T extends Concept> {

    private final T concept;
    
    private final Set<String> matchedTerms;

    /**
     * 
     * @param concept
     * @param matchedTerms
     */
    public NATConceptSearchResult(T concept, Set<String> matchedTerms) {
        this.concept = concept;
        this.matchedTerms = matchedTerms;
    }

    public T getConcept() {
        return concept;
    }
    
    public Set<String> getMatchedTerms() {
        return matchedTerms;
    }
    
    @Override
    public String toString() {
        return concept.getName();
    }
}
