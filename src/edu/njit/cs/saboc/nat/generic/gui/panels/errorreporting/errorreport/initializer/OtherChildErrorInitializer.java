package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.OtherChildError;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class OtherChildErrorInitializer<T extends Concept> extends ErrorReportPanelInitializer<T, OtherChildError<T>> {
    
    private final T erroneousChild;
    
    public OtherChildErrorInitializer(Ontology<T> ontology, T erroneousConcept, T erroneousParent) {
        
        super(ontology, erroneousConcept);

        this.erroneousChild = erroneousParent;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return String.format("<html><font size = '6'><b>Other error with child:</b> %s", erroneousChild.getName());
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.NonCritical;
    }

    @Override
    public OtherChildError<T> generateError(String comment, OntologyError.Severity severity) {
        return new OtherChildError<>(getOntology(), erroneousChild, comment, severity);
    }

    @Override
    public String getErrorTypeName() {
        return "Other Error with Child Concept";
    }
    
}
