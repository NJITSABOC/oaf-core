package edu.njit.cs.saboc.nat.generic.data;

/**
 *
 * @author Chris O
 */
public class BrowserRelationship<REL_T> {
    private REL_T relationship;
    
    private ConceptBrowserDataSource dataSource;
    
    public BrowserRelationship(REL_T relationship, ConceptBrowserDataSource dataSource) {
        this.relationship = relationship;
        this.dataSource = dataSource;
    }
    
    public REL_T getRelationship() {
        return relationship;
    }
    
    public String getRelationshipName() {
        return dataSource.getRelationshipName(this);
    }
    
    public BrowserConcept getRelationshipTarget() {
        return dataSource.getRelationshipTarget(this);
    }
}
