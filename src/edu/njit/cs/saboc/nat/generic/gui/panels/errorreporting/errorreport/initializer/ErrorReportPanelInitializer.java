package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;

/**
 *
 * @author Chris O
 * @param <T>
 * 
 * @param <V>
 */
public abstract class ErrorReportPanelInitializer<T extends Concept, V extends OntologyError<T>> {
    
    private final Ontology<T> ontology;
    private final T erroneousConcept;
    
    public ErrorReportPanelInitializer(Ontology<T> ontology, T erroneousConcept) {
        this.ontology = ontology;
        this.erroneousConcept = erroneousConcept;
    }
    
    public Ontology<T> getOntology() {
        return ontology;
    }
    
    public T getErroneousConcept() {
        return erroneousConcept;
    }
    
    public abstract String getErrorTypeName();
    
    public abstract String getStyledErrorDescriptionText();
    public abstract OntologyError.Severity getDefaultSeverity();
    
    public abstract V generateError(String comment, OntologyError.Severity severity);
}
