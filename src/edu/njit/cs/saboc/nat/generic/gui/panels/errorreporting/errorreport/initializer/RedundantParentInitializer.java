package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.RedundantParentError;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class RedundantParentInitializer<T extends Concept> implements ErrorReportPanelInitializer<RedundantParentError<T>> {
    
    private final Ontology<T> theOntology; 
    private final T erroneousParent;
    
    public RedundantParentInitializer(Ontology<T> ontology, T erroneousParent) {
        this.theOntology = ontology;
        this.erroneousParent = erroneousParent;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return String.format("<html><font size = '6'><b>Remove redundant parent:</b> %s", erroneousParent.getName());
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.NonCritical;
    }

    @Override
    public RedundantParentError<T> generateError(String comment, OntologyError.Severity severity) {
        return new RedundantParentError(theOntology, erroneousParent, comment, severity);
    }

    @Override
    public String getErrorTypeName() {
        return "Reundant Parent Concept";
    }
}