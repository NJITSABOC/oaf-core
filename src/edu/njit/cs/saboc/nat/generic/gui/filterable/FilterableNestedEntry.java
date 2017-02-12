package edu.njit.cs.saboc.nat.generic.gui.filterable;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public abstract class FilterableNestedEntry<T, V> extends Filterable<T> {
    
    private final ArrayList<Filterable<V>> subEntries;
    
    private final T container;
    
    public FilterableNestedEntry(T container, ArrayList<Filterable<V>> subEntries) {
        this.subEntries = subEntries;
        this.container = container;
    }

    @Override
    public void clearFilter() {
        super.clearFilter();
        
        subEntries.forEach( (entry) -> {
            entry.clearFilter();
        });
    }

    @Override
    public void setCurrentFilter(String filter) {
        super.setCurrentFilter(filter);
        
        subEntries.forEach( (entry) -> {
            entry.setCurrentFilter(filter);
        });
    }

    @Override
    public boolean containsFilter(String filter) {
        
        return subEntries.stream().anyMatch( (entry) -> {
            return entry.containsFilter(filter);
        });
        
    }
    
    public ArrayList<Filterable<V>> getSubEntries() {
        return subEntries;
    }

    @Override
    public T getObject() {
        return container;
    }
}
