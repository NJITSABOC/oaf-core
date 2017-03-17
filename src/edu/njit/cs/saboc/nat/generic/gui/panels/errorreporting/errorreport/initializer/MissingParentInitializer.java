package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.parent.MissingParentError;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class MissingParentInitializer<T extends Concept> implements MissingConceptInitializer<T, MissingParentError<T>> {
    
    private final Ontology<T> theOntology; 
    
    public MissingParentInitializer(Ontology<T> ontology) {
        this.theOntology = ontology;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return "<html><font size = '6'><b>Missing Parent</b>";
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.NonCritical;
    }

    @Override
    public MissingParentError<T> generateError(String comment, OntologyError.Severity severity) {
        return new MissingParentError<>(theOntology, comment, severity);
    }

    @Override
    public MissingParentError<T> generateError(String comment, OntologyError.Severity severity, T concept) {
        MissingParentError<T> error = this.generateError(comment, severity);
        error.setMissingParent(concept);
        
        return error;
    }

    @Override
    public String getErrorTypeName() {
        return "Missing Parent Concept";
    }
}