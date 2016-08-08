package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Interface that defines the methods that must be implemented by all Data Field data retrievers
 * @author Chris O
 */
public interface FieldDataRetriever<V> {
    public V retrieveData(Concept concept);
}
