package edu.njit.cs.saboc.nat.generic.errorreport.error.child;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;

/**
 * Represents some type of error with a child concept in relation to a specific
 * audit set concept
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class ChildError<T extends Concept> extends OntologyError<T> {
    
    public ChildError(Ontology<T> ontology) {
        super(ontology, "", OntologyError.Severity.NonCritical);
    }
    
    public ChildError(Ontology<T> ontology, String comment, OntologyError.Severity severity) {
       super(ontology, comment, severity);
    }
}
