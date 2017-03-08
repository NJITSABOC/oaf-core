package edu.njit.cs.saboc.nat.generic.gui.filterable.list;

/**
 * Interface for defining an entry in a filterable list that allows 
 * navigation to another focus concept.
 * 
 * @author Chris O
 * @param <T>
 */
public interface NavigableEntry<T> {
    public T getNavigableConcept();
}
