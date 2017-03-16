package edu.njit.cs.saboc.nat.generic.errorreport.error.child;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.ParentError;
import java.util.Optional;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class MissingChildError<T extends Concept> extends ChildError<T> {
    
    private Optional<T> missingChild = Optional.empty();
    
    public MissingChildError(Ontology<T> ontology) {
        super(ontology, "", OntologyError.Severity.NonCritical);
    }
    
    public MissingChildError(Ontology<T> ontology, String comment, OntologyError.Severity severity) {
        super(ontology, comment, severity);
    }
    
    public void setMissingChild(T missingParent) {
        this.missingChild = Optional.of(missingParent);
    }
    
    public void clearMissingChild() {
        this.missingChild = Optional.empty();
    }
    
    public Optional<T> getMissingChild() {
        return missingChild;
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject errorObject = super.getBaseJSON("MissingChild");
        
        if(missingChild.isPresent()) {
            errorObject.put("childid", missingChild.get().getIDAsString());
        }
        
        return errorObject;
    }
}
