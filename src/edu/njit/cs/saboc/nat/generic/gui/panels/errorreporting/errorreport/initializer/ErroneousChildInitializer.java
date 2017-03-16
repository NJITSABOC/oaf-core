package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.ErroneousChildError;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ErroneousChildInitializer<T extends Concept> implements ErrorReportPanelInitializer<ErroneousChildError<T>> {
    
    private final Ontology<T> theOntology; 
    private final T erroneousChild;
    
    public ErroneousChildInitializer(Ontology<T> ontology, T erroneousParent) {
        this.theOntology = ontology;
        this.erroneousChild = erroneousParent;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return String.format("<html><font size = '6'><b>Remove erroneous child:</b> %s", erroneousChild.getName());
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.NonCritical;
    }

    @Override
    public ErroneousChildError<T> generateError(String comment, OntologyError.Severity severity) {
        return new ErroneousChildError(theOntology, erroneousChild, comment, severity);
    }
}
