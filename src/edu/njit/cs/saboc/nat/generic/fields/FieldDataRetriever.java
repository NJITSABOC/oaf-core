package edu.njit.cs.saboc.nat.generic.fields;

import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;

/**
 *
 * @author Chris O
 */
public interface FieldDataRetriever<T> {
    public T retrieveData(BrowserConcept concept);
}
