package edu.njit.cs.saboc.nat.generic.data;

/**
 *
 * @author Chris O
 */
public abstract class BrowserConcept<CONCEPT_T> {
    private CONCEPT_T concept;
    
    private ConceptBrowserDataSource dataSource;
    
    public BrowserConcept(CONCEPT_T concept, ConceptBrowserDataSource dataSource) {
        this.concept = concept;
        this.dataSource = dataSource;
    }
    
    public CONCEPT_T getConcept() {
        return concept;
    }
    
    public String getName() {
        return dataSource.getConceptName(this);
    }
    
    public String getId() {
        return dataSource.getConceptId(this);
    }
    
    public String toString() {
        return String.format("%s {%s}", getName(), getId());
    }
}
