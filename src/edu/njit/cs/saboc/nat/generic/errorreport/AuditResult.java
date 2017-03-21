
package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AuditResult<T extends Concept> {
    
    public static enum State {
        Unaudited,
        Error,
        Correct
    }
    
    private final ArrayList<OntologyError<T>> errors;
    
    private State state;
    
    private String comment;
        
    public AuditResult() {
        this(State.Unaudited, "", new ArrayList<>());
    }
    
    public AuditResult(State state, String comment, ArrayList<OntologyError<T>> errors) {
        this.state = state;
        this.comment = comment;
        this.errors = errors;
    }
    
    public void setAuditState(State state) {
        this.state = state;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getComment() {
        return comment;
    }
    
    public State getState() {
        return state;
    }
        
    public boolean isCorrect() {
        return this.state == State.Correct;
    }
    
    public boolean isErroneous() {
        return this.state == State.Error;
    }
        
    public void addError(OntologyError<T> error) {
        
        if(error == null) {
            return;
        }
        
        errors.add(error);
        
        this.state = State.Error;
    }
    
    public void removeError(OntologyError<T> error) {
        errors.remove(error);
        
        if(errors.isEmpty()) {
            this.state = State.Unaudited;
        }
    }
        
    public ArrayList<OntologyError<T>> getErrors() {
        return errors;
    }
    
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        
        json.put("state", this.state.toString());
        json.put("comment", comment);
        
        if(!errors.isEmpty()) {
            JSONArray errorJSON = new JSONArray();
            
            this.errors.forEach( (error) -> {
                errorJSON.add(error.toJSON());
            });
            
            json.put("errors", errorJSON);
        }
        
        return json;
    }
}
