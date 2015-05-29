package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.BrowserSearchResult;

/**
 *
 * @author Chris
 */
public class FilterableSearchEntry<T> extends Filterable<BrowserSearchResult<T>> implements NavigableEntry<T> {
    private BrowserSearchResult<T> entry;

    public FilterableSearchEntry(BrowserSearchResult<T> entry) {
        this.entry = entry;
    }
    
    public BrowserSearchResult<T> getObject() {
        return entry;
    }

    public T getNavigableConcept() {
        return entry.getConcept();
    }

    public String getInitialText() {
        return String.format("<html>%s &nbsp;<font color='blue'>{%s}</font>", 
                entry.getName(),
                entry.getConceptId());
    }

    public String getFilterText(String filter) {
        return String.format("<html>%s &nbsp; <font color='purple'>--%s</font>", 
                filter(entry.getName(), filter),
                filter(entry.getConceptId(), filter));
    }
    
    public boolean containsFilter(String filter) {
        return entry.getName().toLowerCase().contains(filter) || 
                entry.getConceptId().contains(filter);
    }
}
