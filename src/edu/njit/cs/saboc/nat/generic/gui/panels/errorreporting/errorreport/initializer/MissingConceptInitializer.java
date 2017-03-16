package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError.Severity;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public interface MissingConceptInitializer<T extends Concept, V extends OntologyError<T>> extends ErrorReportPanelInitializer<V> {
    public V generateError(String comment, Severity severity, T concept);
}
