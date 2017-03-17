package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.ErroneousParentError;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ErroneousParentInitializer<T extends Concept> implements ErrorReportPanelInitializer<ErroneousParentError<T>> {
    
    private final Ontology<T> theOntology; 
    private final T erroneousParent;
    
    public ErroneousParentInitializer(Ontology<T> ontology, T erroneousParent) {
        this.theOntology = ontology;
        this.erroneousParent = erroneousParent;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return String.format("<html><font size = '6'><b>Remove erroneous parent:</b> %s", erroneousParent.getName());
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.Severe;
    }

    @Override
    public ErroneousParentError<T> generateError(String comment, OntologyError.Severity severity) {
        return new ErroneousParentError(theOntology, erroneousParent, comment, severity);
    }

    @Override
    public String getErrorTypeName() {
        return "Erroneous Parent Concept";
    }
}
