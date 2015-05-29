package edu.njit.cs.saboc.nat.generic.fields;

/**
 *
 * @author Chris O
 */
public interface FieldDataRetriever<T, V> {
    public V retrieveData(T concept);
}
