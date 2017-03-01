
package edu.njit.cs.saboc.nat.generic.errorreport;

import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;

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
    
    private final ArrayList<OntologyError<T>> errors = new ArrayList<>();
    
    private State state;
    
    private String comment;
        
    public AuditResult() {
        this.state = State.Unaudited;
        this.comment = "";
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
    
    public void setCorrect(boolean value) {
        if (value) {
            this.state = State.Correct;
        } else {
            this.state = State.Unaudited;
        }
    }
    
    public boolean isCorrect() {
        return this.state == State.Correct;
    }
    
    public boolean isErroneous() {
        return this.state == State.Error;
    }
        
    public void addError(OntologyError<T> error) {
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
}
