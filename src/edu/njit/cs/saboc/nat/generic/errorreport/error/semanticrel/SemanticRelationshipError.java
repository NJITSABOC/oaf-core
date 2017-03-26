package edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class SemanticRelationshipError<T extends Concept> extends OntologyError<T> {
    
    public SemanticRelationshipError(Ontology<T> ontology) {
        super(ontology, "", OntologyError.Severity.NonCritical);
    }
    
    public SemanticRelationshipError(
            Ontology<T> ontology, 
            String comment, 
            OntologyError.Severity severity) {
        
       super(ontology, comment, severity);
    }
    
    public static String generateStyledRelationshipText(String propertyName, String targetName) {
        return String.format("<html><font color = 'RED'>==</font> %s <font color = 'RED'>==></font> %s", 
                        propertyName,
                        targetName);
    }
}
