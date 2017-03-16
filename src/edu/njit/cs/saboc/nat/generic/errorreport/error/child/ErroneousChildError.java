package edu.njit.cs.saboc.nat.generic.errorreport.error.child;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class ErroneousChildError<T extends Concept> extends IncorrectChildError<T> {
    
    public ErroneousChildError(Ontology<T> ontology, T erroneousChild) {
        super(ontology, erroneousChild);
    }
    
    public ErroneousChildError(
            Ontology<T> ontology, 
            T erroneousChild, 
            String comment, 
            OntologyError.Severity severity) {
        
        super(ontology, erroneousChild, comment, severity);
    }
    
    @Override
    public JSONObject toJSON() {
        return super.getBaseJSON("ErroneousChild");
    }
}