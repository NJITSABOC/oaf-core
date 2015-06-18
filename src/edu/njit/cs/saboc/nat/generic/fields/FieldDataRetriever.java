package edu.njit.cs.saboc.nat.generic.fields;

/**
 * Interface that defines the methods that must be implemented by all Data Field data retrievers
 * @author Chris O
 */
public interface FieldDataRetriever<T, V> {
    public V retrieveData(T concept);
}
