package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.OtherParentError;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class OtherParentErrorInitializer<T extends Concept> extends ErrorReportPanelInitializer<T, OtherParentError<T>> {
    
    private final T erroneousParent;
    
    public OtherParentErrorInitializer(Ontology<T> ontology, T erroneousConcept, T erroneousParent) {
        
        super(ontology, erroneousConcept);
        
        this.erroneousParent = erroneousParent;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return String.format("<html><font size = '6'><b>Other error with parent:</b> %s", erroneousParent.getName());
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.NonCritical;
    }

    @Override
    public OtherParentError<T> generateError(String comment, OntologyError.Severity severity) {
        return new OtherParentError(getOntology(), erroneousParent, comment, severity);
    }

    @Override
    public String getErrorTypeName() {
        return "Other Error with Parent Concept";
    }
    
}
