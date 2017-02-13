package edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.concept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.FilterableNestedEntry;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.ExtendedNeighborhoodResult;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class FilterableExtendedNeighborhoodEntry<T extends Concept, 
        V extends ExtendedNeighborhoodResult<T>> extends FilterableNestedEntry<V, T>  {
    
    private final Filterable<T> directNeighborhoodEntry;
    
    public FilterableExtendedNeighborhoodEntry(V result, 
            Filterable<T> directNeighborhoodEntry,
            ArrayList<Filterable<T>> expandedNeighborhoodEntries) {
        
        super(result, expandedNeighborhoodEntries);
        
        this.directNeighborhoodEntry = directNeighborhoodEntry;
    }
    
    public Filterable<T> getDirectNeighborhoodEntry() {
        return directNeighborhoodEntry;
    }
    
    public ArrayList<Filterable<T>> getExpandedNeighborhoodEntries() {
        return super.getSubEntries();
    }

    @Override
    public boolean containsFilter(String filter) {
        return super.containsFilter(filter) || directNeighborhoodEntry.containsFilter(filter);
    }

    @Override
    public void setCurrentFilter(String filter) {
        super.setCurrentFilter(filter);
        
        this.directNeighborhoodEntry.setCurrentFilter(filter);
    }

    @Override
    public void clearFilter() {
        super.clearFilter();
        
        this.directNeighborhoodEntry.clearFilter();
    }

    @Override
    public String getToolTipText() {
        return "";
    }

    @Override
    public String getClipboardText() {
        return "";
    }
}
