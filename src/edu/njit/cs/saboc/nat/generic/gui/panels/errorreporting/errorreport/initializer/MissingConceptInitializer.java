package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError.Severity;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public abstract class MissingConceptInitializer<T extends Concept, V extends OntologyError<T>> 
        extends ErrorReportPanelInitializer<T, V> {
    
    public MissingConceptInitializer(Ontology<T> ontology, T erroneousConcept) {
        super(ontology, erroneousConcept);
    }
    
    public abstract V generateError(String comment, Severity severity, T concept);
}
