package edu.njit.cs.saboc.nat.generic.errorreport.error.child;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import org.json.simple.JSONObject;

/**
 * Represents an error where a given child concept is erroneously a child
 * of a specific audit set concept
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class IncorrectChildError<T extends Concept> extends ChildError<T> {
    
    private final T erroneousChild;
    
    public IncorrectChildError(Ontology<T> ontology, T erroneousChild) {
        super(ontology);
        
        this.erroneousChild = erroneousChild;
    }
    
    public IncorrectChildError(Ontology<T> ontology, T erroneousChild, String comment, OntologyError.Severity severity) {
        super(ontology, comment, severity);
        
        this.erroneousChild = erroneousChild;
    }
    
    public T getErroneousChild() {
        return erroneousChild;
    }
    
    @Override
    public JSONObject getBaseJSON(String errorType) {
        JSONObject baseJSON = super.getBaseJSON(errorType);
        baseJSON.put("childid", erroneousChild.getIDAsString());
        
        return baseJSON;
    }
}
