package edu.njit.cs.saboc.nat.generic.errorreport.error.parent;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;

/**
 * Base class for any kind of error where the parent should be removed
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class RemoveParentError<T extends Concept> extends IncorrectParentError<T> {
    
    public RemoveParentError(Ontology<T> ontology, T erroneousParent) {
        super(ontology, erroneousParent);
    }
    
    public RemoveParentError(
            Ontology<T> ontology, 
            T erroneousParent, 
            String comment, 
            OntologyError.Severity severity) {
        
        super(ontology, erroneousParent, comment, severity);
    }
}
