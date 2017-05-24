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
public class ErroneousChildInitializer<T extends Concept> extends ErrorReportPanelInitializer<T, ErroneousChildError<T>> {
     
    private final T erroneousChild;
    
    public ErroneousChildInitializer(
            Ontology<T> ontology, 
            T erroneousConcept, 
            T erroneousChild) {
        
        super(ontology, erroneousConcept);
        
        this.erroneousChild = erroneousChild;
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
        return new ErroneousChildError(getOntology(), erroneousChild, comment, severity);
    }

    @Override
    public String getErrorTypeName() {
        return "Erroneous Child Concept";
    }
}
