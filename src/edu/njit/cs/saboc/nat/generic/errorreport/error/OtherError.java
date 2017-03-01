package edu.njit.cs.saboc.nat.generic.errorreport.error;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class OtherError<T extends Concept> extends OntologyError<T> {
    
    private String description;
    
    public OtherError(Ontology<T> ontology) {
        this(ontology, "", "", Severity.NonCritical);
    }
    
    public OtherError(Ontology<T> ontology, 
            String description, 
            String comment, 
            Severity severity) {
        
        super(ontology, comment, severity);
        
        this.description = description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
