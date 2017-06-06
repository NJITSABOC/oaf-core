package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
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
 * @param <U>
 */
public abstract class MissingRelationshipInitializer<T extends Concept, V extends InheritableProperty, U extends OntologyError<T>> 
                extends MissingConceptInitializer<T, U> {
    
    public MissingRelationshipInitializer(Ontology<T> ontology, T erroneousConcept) {
        super(ontology, erroneousConcept);
    }
    
    public abstract U generateError(V propertyType, String comment, Severity severity);
    public abstract U generateError(V propertyType, T targetConcept, String comment, Severity severity);
}
