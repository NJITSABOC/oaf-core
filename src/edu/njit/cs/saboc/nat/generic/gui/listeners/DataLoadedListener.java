package edu.njit.cs.saboc.nat.generic.gui.listeners;

/**
 * Listener for when data has been loaded into a result panel
 * 
 * @author Chris O
 * @param <T>
 */
public interface DataLoadedListener<T> {
    public void dataLoaded(T data);
}
