package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError.Severity;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.ReplaceParentError;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class ReplaceParentInitializer<T extends Concept> extends MissingConceptInitializer<T, ReplaceParentError<T>>  {
    
    private final T erroneousParent;
    
    public ReplaceParentInitializer(Ontology<T> ontology, T erroneousConcept, T erroneousParent) {
        
        super(ontology, erroneousConcept);
        
        this.erroneousParent = erroneousParent;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return String.format("<html><font size = '6'><b>Replace erroneous parent:</b> %s", erroneousParent.getName());
    }

    @Override
    public Severity getDefaultSeverity() {
        return Severity.Severe;
    }

    @Override
    public ReplaceParentError<T> generateError(String comment, Severity severity) {
        return new ReplaceParentError<>(getOntology(), erroneousParent, comment, severity);
    }
    
    @Override
    public ReplaceParentError<T> generateError(String comment, Severity severity, T replacementConcept) {
        ReplaceParentError<T> error = this.generateError(comment, severity);
        error.setReplacementParent(replacementConcept);

        return error;
    }

    @Override
    public String getErrorTypeName() {
        return "Replace Erroneous Parent Concept";
    }
}
