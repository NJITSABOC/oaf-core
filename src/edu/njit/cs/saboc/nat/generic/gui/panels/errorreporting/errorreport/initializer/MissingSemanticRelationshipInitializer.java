package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError.Severity;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.MissingSemanticRelationshipError;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class MissingSemanticRelationshipInitializer<T extends Concept, V extends InheritableProperty> 
    implements MissingRelationshipInitializer<T, V, MissingSemanticRelationshipError<T, V>> {
    
    private final Ontology<T> ontology;
    
    public MissingSemanticRelationshipInitializer(Ontology<T> ontology) {
        this.ontology = ontology;
    }


    @Override
    public String getStyledErrorDescriptionText() {
        return String.format("<html><font size = '6'><b>Missing relationship</b>");
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return Severity.NonCritical;
    }

    @Override
    public MissingSemanticRelationshipError<T, V> generateError(String comment, OntologyError.Severity severity) {
        return new MissingSemanticRelationshipError<>(ontology, comment, severity);
    }
    
    @Override
    public MissingSemanticRelationshipError<T, V> generateError(V propertyType, String comment, OntologyError.Severity severity) {
        MissingSemanticRelationshipError<T, V> error = this.generateError(comment, severity);
        
        error.setMissingRelType(propertyType);
        
        return error;
    }

    @Override
    public MissingSemanticRelationshipError<T, V> generateError(V propertyType, T targetConcept, String comment, OntologyError.Severity severity) {
        MissingSemanticRelationshipError<T, V> error = this.generateError(propertyType, comment, severity);
        error.setMissingRelTarget(targetConcept);
        
        return error;
    }

    @Override
    public MissingSemanticRelationshipError<T, V> generateError(String comment, OntologyError.Severity severity, T concept) {
        MissingSemanticRelationshipError<T, V> error = this.generateError(comment, severity);
        error.setMissingRelTarget(concept);
        
        return error;
    }

    @Override
    public String getErrorTypeName() {
        return "Missing Semantic Relationship";
    }
}
