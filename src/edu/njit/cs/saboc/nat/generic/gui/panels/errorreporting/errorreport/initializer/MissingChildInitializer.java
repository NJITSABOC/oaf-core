package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.child.MissingChildError;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class MissingChildInitializer<T extends Concept> implements MissingConceptInitializer<T, MissingChildError<T>> {
    
    private final Ontology<T> theOntology; 
    
    public MissingChildInitializer(Ontology<T> ontology) {
        this.theOntology = ontology;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        return "<html><font size = '6'><b>Missing Child</b>";
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.NonCritical;
    }

    @Override
    public MissingChildError<T> generateError(String comment, OntologyError.Severity severity) {
        return new MissingChildError<>(theOntology, comment, severity);
    }

    @Override
    public MissingChildError<T> generateError(String comment, OntologyError.Severity severity, T concept) {
        MissingChildError<T> error = this.generateError(comment, severity);
        error.setMissingChild(concept);
        
        return error;
    }

    @Override
    public String getErrorTypeName() {
        return "Missing Child Concept";
    }
}