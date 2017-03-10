package edu.njit.cs.saboc.nat.generic.errorreport.error.parent;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Optional;

/**
 * A report of a parent missing from a concept
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class MissingParentError<T extends Concept> extends ParentError<T> {
    
    private Optional<T> missingParent = Optional.empty();
    
    public MissingParentError(Ontology<T> ontology) {
        super(ontology, "", Severity.NonCritical);
    }
    
    public MissingParentError(Ontology<T> ontology, String comment, Severity severity) {
       super(ontology, comment, severity);
    }
    
    public void setMissingParent(T missingParent) {
        this.missingParent = Optional.of(missingParent);
    }
    
    public void clearMissingParent() {
        this.missingParent = Optional.empty();
    }
    
    public Optional<T> getMissingParent() {
        return missingParent;
    }
}
