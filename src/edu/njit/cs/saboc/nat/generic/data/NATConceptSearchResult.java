package edu.njit.cs.saboc.nat.generic.data;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

public class NATConceptSearchResult<T extends Concept> {

    private final T concept;
    
    private final Set<String> matchedTerms;
    
    private final String query;

    /**
     * 
     * @param concept
     * @param matchedTerms
     * @param query
     */
    public NATConceptSearchResult(T concept, Set<String> matchedTerms, String query) {
        this.concept = concept;
        this.matchedTerms = matchedTerms;
        this.query = query;
    }

    public T getConcept() {
        return concept;
    }
    
    public Set<String> getMatchedTerms() {
        return matchedTerms;
    }
    
    public String getQuery() {
        return query;
    }
    
    @Override
    public String toString() {
        return concept.getName();
    }
}
