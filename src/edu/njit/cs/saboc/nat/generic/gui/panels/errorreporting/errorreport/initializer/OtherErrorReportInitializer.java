package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError.Severity;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OtherError;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class OtherErrorReportInitializer<T extends Concept> implements ErrorReportPanelInitializer<OtherError<T>> {
    
    private final Ontology<T> ontology;
    
    public OtherErrorReportInitializer(Ontology<T> ontology) {
        this.ontology = ontology;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return "<html><font size = '6'><b>Other Error</b>";
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return Severity.NonCritical;
    }

    @Override
    public OtherError<T> generateError(String comment, OntologyError.Severity severity) {
        return new OtherError<>(ontology, comment, severity);
    }

    @Override
    public String getErrorTypeName() {
        return "Other Type of Error";
    }
}
