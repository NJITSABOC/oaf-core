package edu.njit.cs.saboc.nat.generic.data;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * A class that represents a search result for the Search panel
 * 
 * @author Chris O
 */
public class BrowserSearchResult {

    private final Concept concept;
    
    private final String matchedTerm;

    /**
     * 
     * @param concept  
     * @param matchedTerm
     */
    public BrowserSearchResult(Concept concept, String matchedTerm) {
        this.concept = concept;
        this.matchedTerm = matchedTerm;
    }

    public Concept getConcept() {
        return concept;
    }
    
    public String getMatchedTerm() {
        return matchedTerm;
    }

    public String toString() {
        return String.format("%s {%s}", concept.getName(), concept.getIDAsString());
    }
}
