package edu.njit.cs.saboc.nat.generic.errorreport.error.parent;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import org.json.simple.JSONObject;

/**
 * An error report involving a parent that is erroneous and should be removed
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class ErroneousParentError<T extends Concept> extends RemoveParentError<T> {
    
    public ErroneousParentError(Ontology<T> ontology, T erroneousParent) {
        super(ontology, erroneousParent);
    }
    
    public ErroneousParentError(
            Ontology<T> ontology, 
            T erroneousParent, 
            String comment, 
            OntologyError.Severity severity) {
        
        super(ontology, erroneousParent, comment, severity);
    }
    
    @Override
    public JSONObject toJSON() {
        return super.getBaseJSON("ErroneousParent");
    }

    @Override
    public String getSummaryText() {
        return String.format("Erroneous parent: %s", this.getErroneousParent().getName());
    }

    @Override
    public String getTooltipText() {
        return "Remove parent (erroneous)";
    }
}