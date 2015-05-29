package edu.njit.cs.saboc.nat.generic.data;

/**
 *
 * @author Chris O
 */
public class BrowserSearchResult<T> {

    /**
     * The term of the search result
     */
    private String conceptName;
    
    private String conceptId;

    private T concept;

    public BrowserSearchResult(String conceptName, String conceptId, T concept) {
        this.conceptName = conceptName;
        this.conceptId = conceptId;
        this.concept = concept;
    }

    /**
     * Returns the term of the search result
     *
     * @return The term of the search result
     */
    public String getName() {
        return conceptName;
    }
    
    public String getConceptId() {
        return conceptId;
    }

    /**
     * Returns the fully specified name of the concept matched to the found term
     *
     */
    public T getConcept() {
        return concept;
    }

    public String toString() {
        return String.format("%s {%s}", conceptName, conceptId);
    }
}
