package edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import java.util.ArrayList;

/**
 * Generic type for nested entries in a nested filterable list. 
 * Contains an list of subentries. 
 * 
 * @author Chris O
 * 
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
    

    /**
     * A nested filterable entry is matched to a filter if any of its
     * sub entries contain the filter
     * 
     * @param filter
     * @return 
     */
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
