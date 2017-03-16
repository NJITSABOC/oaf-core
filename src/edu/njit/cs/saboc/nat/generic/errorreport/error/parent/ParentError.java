package edu.njit.cs.saboc.nat.generic.errorreport.error.parent;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;

/**
 * Base class for all error reports related to parents
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class ParentError<T extends Concept> extends OntologyError<T> {
    
    public ParentError(Ontology<T> ontology) {
        super(ontology, "", Severity.NonCritical);
    }
    
    public ParentError(Ontology<T> ontology, String comment, Severity severity) {
       super(ontology, comment, severity);
    }
}
