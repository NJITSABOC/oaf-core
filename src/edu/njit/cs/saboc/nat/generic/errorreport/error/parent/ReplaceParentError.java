package edu.njit.cs.saboc.nat.generic.errorreport.error.parent;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Optional;
import org.json.simple.JSONObject;

/**
 * A report of a parent that should be replaced by another parent
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class ReplaceParentError<T extends Concept> extends IncorrectParentError<T> {
 
    private Optional<T> replacementParent = Optional.empty();
    
    public ReplaceParentError(Ontology<T> ontology, T erroneousParent) {
        super(ontology, erroneousParent);
    }
    
    public ReplaceParentError(Ontology<T> ontology, T erroneousParent, String comment, Severity severity) {
        super(ontology, erroneousParent, comment, severity);
    }
    
    public void setReplacementParent(T replacementParent) {
        this.replacementParent = Optional.of(replacementParent);
    }
    
    public void clearReplacementParent() {
        this.replacementParent = Optional.empty();
    }
    
    public Optional<T> getReplacementParent() {
        return replacementParent;
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject errorObject = super.getBaseJSON("ReplaceParent");
        
        if(replacementParent.isPresent()) {
            errorObject.put("replacementparentid", this.getReplacementParent().get().getIDAsString());
        }
        
        return errorObject;
    }
}
