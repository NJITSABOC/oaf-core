package edu.njit.cs.saboc.nat.generic.errorreport.error;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Optional;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class IncorrectParentError<T extends Concept> extends OntologyError<T> {
    
    private final T incorrectParent;
    
    private Optional<T> suggestedParent;
    
    public IncorrectParentError(
            Ontology<T> ontology, 
            T incorrectParent,
            Optional<T> suggestedParent,
            String comment, 
            Severity severity) {
        
        super(ontology, comment, severity);
        
        this.incorrectParent = incorrectParent;
        this.suggestedParent = suggestedParent;
    }
    
    public T getIncorrectParent() {
        return incorrectParent;
    }
    
    public Optional<T> getSuggestedParent() {
        return suggestedParent;
    }
    
    public void setSuggestedParent(T concept) {
        this.suggestedParent = Optional.of(concept);
    }
    
    public void removeSuggestedParent() {
        this.suggestedParent = Optional.empty();
    }
}
