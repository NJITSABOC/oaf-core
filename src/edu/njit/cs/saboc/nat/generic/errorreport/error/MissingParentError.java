package edu.njit.cs.saboc.nat.generic.errorreport.error;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Optional;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class MissingParentError<T extends Concept> extends OntologyError<T> {
    
    private Optional<T> suggestedParent;
    
    public MissingParentError(
            Ontology<T> ontology, 
            Optional<T> suggestedParent,
            String comment, 
            Severity severity) {
        
        super(ontology, comment, severity);
        
        this.suggestedParent = suggestedParent;
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
