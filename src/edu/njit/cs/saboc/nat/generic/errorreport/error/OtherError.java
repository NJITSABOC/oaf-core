package edu.njit.cs.saboc.nat.generic.errorreport.error;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class OtherError<T extends Concept> extends OntologyError<T> {

    public OtherError(Ontology<T> ontology) {
        this(ontology, "", Severity.NonCritical);
    }
    
    public OtherError(Ontology<T> ontology, 
            String comment, 
            Severity severity) {
        
        super(ontology, comment, severity);
    }

    @Override
    public JSONObject toJSON() {
        return super.getBaseJSON("OtherError");
    }
}
