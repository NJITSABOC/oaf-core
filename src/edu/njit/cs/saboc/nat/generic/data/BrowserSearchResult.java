package edu.njit.cs.saboc.nat.generic.data;

/**
 *
 * @author Chris O
 */
public class BrowserSearchResult {

    /**
     * The term of the search result
     */
    private String term;

    private BrowserConcept concept;

    public BrowserSearchResult(String term, BrowserConcept concept) {
        this.term = term;
        this.concept = concept;
    }

    /**
     * Returns the term of the search result
     *
     * @return The term of the search result
     */
    public String getName() {
        return term;
    }

    /**
     * Returns the fully specified name of the concept matched to the found term
     *
     */
    public BrowserConcept getConcept() {
        return concept;
    }

    public String toString() {
        return String.format("%s {%s} (%s)", term, concept.getName(), concept.getId());
    }
}
