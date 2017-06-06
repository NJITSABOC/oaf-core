package edu.njit.cs.saboc.nat.generic.errorreport.error.parent;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * Represents some kind of error in an existing parent of a concept
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class IncorrectParentError<T extends Concept> extends ParentError<T> {
    
    private final T incorrectParent;
    
    public IncorrectParentError(Ontology<T> ontology, T erroneousParent) {
        super(ontology);
        
        this.incorrectParent = erroneousParent;
    }
    
    public IncorrectParentError(Ontology<T> ontology, T erroneousParent, String comment, Severity severity) {
        super(ontology, comment, severity);
        
        this.incorrectParent = erroneousParent;
    }
    
    public T getIncorrectParent() {
        return incorrectParent;
    }
    
    @Override
    public JSONObject getBaseJSON(String errorType) {
        JSONObject baseJSON = super.getBaseJSON(errorType);
        baseJSON.put("parentid", incorrectParent.getIDAsString());
        
        return baseJSON;
    }
}
