package edu.njit.cs.saboc.nat.generic.errorreport.error;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Optional;

/**
 *
 * @author Chris O
 * 
 * @param <T> The type of the concept
 * @param <V> The type of the relationships
 */
public class MissingSemanticRelationshipError<T extends Concept, V> extends OntologyError<T> {
    
    private Optional<T> suggestedTarget;
    private Optional<V> suggestedRelationshipType;
    
    public MissingSemanticRelationshipError(
            Ontology<T> ontology, 
            Optional<T> suggestedTarget,
            Optional<V> suggestedRelationship,
            String comment, 
            Severity severity) {
        
        super(ontology, comment, severity);
        
        this.suggestedTarget = suggestedTarget;
        this.suggestedRelationshipType = suggestedRelationship;
    }
    
    public Optional<T> getSuggestedTarget() {
        return suggestedTarget;
    }
    
    public Optional<V> getSuggestedRelationhip() {
        return suggestedRelationshipType;
    }
    
    public void setSuggestedTarget(T concept) {
        this.suggestedTarget = Optional.of(concept);
    }
    
    public void setSuggestedRelationship(V relationship) {
        this.suggestedRelationshipType = Optional.of(relationship);
    }
    
    public void clearSuggestedTarget() {
        this.suggestedTarget = Optional.empty();
    }
    
    public void clearSuggestedRelationship() {
        this.suggestedRelationshipType = Optional.empty();
    }
}
