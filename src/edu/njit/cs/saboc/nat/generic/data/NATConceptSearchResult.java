package edu.njit.cs.saboc.nat.generic.data;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * The result of searching for and finding a concept in an ontology
 * 
 * @author cro3
 * @param <T> 
 */
public class NATConceptSearchResult<T extends Concept> {

    // The result concept
    private final T concept;
    
    // The set of terms that matched the query
    private final Set<String> matchedTerms;
    
    // The query that was matched to the above terms
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
