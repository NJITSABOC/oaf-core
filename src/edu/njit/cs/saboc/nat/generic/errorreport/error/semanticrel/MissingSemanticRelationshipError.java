
package edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.util.Optional;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class MissingSemanticRelationshipError<T extends Concept, V extends InheritableProperty> extends SemanticRelationshipError<T> {

    private Optional<V> missingRelType = Optional.empty();
    private Optional<T> missingTarget = Optional.empty();

    public MissingSemanticRelationshipError(Ontology<T> ontology) {
        super(ontology, "", OntologyError.Severity.NonCritical);
    }

    public MissingSemanticRelationshipError(Ontology<T> ontology, String comment, OntologyError.Severity severity) {
        super(ontology, comment, severity);
    }
    
    public void setMissingRelType(V relType) {
        this.missingRelType = Optional.of(relType);
    }
    
    public void clearMissingRelType() {
        this.missingRelType = Optional.empty();
    }
    
    public Optional<V> getMissingRelType() {
        return missingRelType;
    }
    
    public void setMissingRelTarget(T target) {
        this.missingTarget = Optional.of(target);
    }
    
    public void clearMissingRelTarget() {
        this.missingTarget = Optional.empty();
    }
    
    public Optional<T> getMissingTarget() {
        return missingTarget;
    }
}
