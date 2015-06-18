package edu.njit.cs.saboc.nat.generic.data;

/**
 * A class that represents a search result for the Search panel
 * 
 * @author Chris O
 */
public class BrowserSearchResult<T> {

    /**
     * The name of the concepts which is a search result (may not be preferred name)
     */
    private String conceptName;
    
    /**
     * The unique ID of the concept that is a search result
     */
    private String conceptId;

    /**
     * The concept that is a search result
     */
    private T concept;

    /**
     * 
     * @param conceptName The name (or a synonym, potentially) of the search result concept
     * @param conceptId The unique ID of the search result concept
     * @param concept  The search result concept
     */
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
    
    /**
     * 
     * @return The unique ID of the search result concept
     */
    public String getConceptId() {
        return conceptId;
    }


    /**
     * 
     * @return The search result concept
     */
    public T getConcept() {
        return concept;
    }

    public String toString() {
        return String.format("%s {%s}", conceptName, conceptId);
    }
}
