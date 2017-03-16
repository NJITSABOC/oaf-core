package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.ReplaceSemanticRelationshipError;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class ReplaceSemanticRelationshipInitializer <T extends Concept, V extends InheritableProperty> 
    implements MissingRelationshipInitializer<T, V, ReplaceSemanticRelationshipError<T, V>> {
    
    private final Ontology<T> ontology;
    
    private final V incorrectProperty;
    private final T incorrectTarget;
    
    public ReplaceSemanticRelationshipInitializer(Ontology<T> ontology, V incorrectProperty, T incorrectTarget) {
        this.ontology = ontology;
        
        this.incorrectProperty = incorrectProperty;
        this.incorrectTarget = incorrectTarget;
    }

    @Override
    public String getStyledErrorDescriptionText() {
         return String.format(""
                + "<html><font size = '6'><b>Replace erroneous relationship:</b><br>"
                + "<b><font color='red'> == </font><i>%s</i> <font color='red'>==></font></b> %s", 
                incorrectProperty.getName(), 
                incorrectTarget.getName());
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.Moderate;
    }

    @Override
    public ReplaceSemanticRelationshipError<T, V> generateError(String comment, OntologyError.Severity severity) {
        return new ReplaceSemanticRelationshipError<>(ontology, incorrectProperty, incorrectTarget, comment, severity);
    }
    
    @Override
    public ReplaceSemanticRelationshipError<T, V> generateError(V propertyType, String comment, OntologyError.Severity severity) {
        ReplaceSemanticRelationshipError<T, V> error = this.generateError(comment, severity);
        
        error.setReplacementRelationshipType(propertyType);
        
        return error;
    }

    @Override
    public ReplaceSemanticRelationshipError<T, V> generateError(V propertyType, T targetConcept, String comment, OntologyError.Severity severity) {
        ReplaceSemanticRelationshipError<T, V> error = this.generateError(propertyType, comment, severity);
        error.setReplacementTarget(targetConcept);
        
        return error;
    }

    @Override
    public ReplaceSemanticRelationshipError<T, V> generateError(String comment, OntologyError.Severity severity, T concept) {
        ReplaceSemanticRelationshipError<T, V> error = this.generateError(comment, severity);
        error.setReplacementTarget(concept);
        
        return error;
    }
}