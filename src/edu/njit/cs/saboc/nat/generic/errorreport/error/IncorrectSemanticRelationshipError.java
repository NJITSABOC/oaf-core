package edu.njit.cs.saboc.nat.generic.errorreport.error;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Optional;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class IncorrectSemanticRelationshipError<T extends Concept, V> extends OntologyError<T> {
    
    private T incorrectTarget;
    private V incorrectRelationshipType;
    
    private Optional<T> correctedTarget;
    private Optional<V> correctedRelationshipType;
    
    public IncorrectSemanticRelationshipError(
            Ontology<T> ontology, 
            Optional<T> correctedTarget,
            Optional<V> correctedRelationshipType,
            String comment, 
            Severity severity) {
        
        super(ontology, comment, severity);
        
        this.correctedTarget = correctedTarget;
        this.correctedRelationshipType = correctedRelationshipType;
    }
    
    public T getIncorrectTarget() {
        return incorrectTarget;
    }
    
    public V getIncorrectRelationshipType() {
        return incorrectRelationshipType;
    }
    
    public Optional<T> getSuggestedTarget() {
        return correctedTarget;
    }
    
    public Optional<V> getSuggestedRelationhip() {
        return correctedRelationshipType;
    }
    
    public void setSuggestedTarget(T concept) {
        this.correctedTarget = Optional.of(concept);
    }
    
    public void setSuggestedRelationship(V relationship) {
        this.correctedRelationshipType = Optional.of(relationship);
    }
    
    public void clearSuggestedTarget() {
        this.correctedTarget = Optional.empty();
    }
    
    public void clearSuggestedRelationship() {
        this.correctedRelationshipType = Optional.empty();
    }
}
