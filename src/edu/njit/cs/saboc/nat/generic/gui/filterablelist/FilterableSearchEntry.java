package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.BrowserSearchResult;

/**
 * A filterable entry for a search result list
 * @author Chris
 */
public class FilterableSearchEntry<T> extends Filterable<BrowserSearchResult<T>> implements NavigableEntry<T> {
   
    /**
     * The search result
     */
    private BrowserSearchResult<T> entry;

    /**
     * 
     * @param entry The search result
     */
    public FilterableSearchEntry(BrowserSearchResult<T> entry) {
        this.entry = entry;
    }
    
    /**
     * 
     * @return The search result
     */
    public BrowserSearchResult<T> getObject() {
        return entry;
    }

    /**
     * 
     * @return The search result concept
     */
    public T getNavigableConcept() {
        return entry.getConcept();
    }

    public String getInitialText() {
        return String.format("<html>%s &nbsp;<font color='blue'>{%s}</font>", 
                entry.getName(),
                entry.getConceptId());
    }
    
    public String getInitialText(boolean showURIs) {
        if (showURIs) return getInitialText();
        else return String.format("<html>%s &nbsp;", entry.getName());
    }

    public String getFilterText(String filter) {
        return String.format("<html>%s &nbsp; <font color='purple'>--%s</font>", 
                filter(entry.getName(), filter),
                filter(entry.getConceptId(), filter));
    }
    
    public String getFilterText(String filter, boolean showURIs) {
        if (showURIs) return getFilterText(filter);
        else return String.format("<html>%s &nbsp;", 
                filter(entry.getName(), filter));
    }
    
    /**
     * 
     * @param filter The filter
     * @return True if the search result's term or search result concept's unique ID contains the filter
     */
    public boolean containsFilter(String filter) {
        return entry.getName().toLowerCase().contains(filter) || 
                entry.getConceptId().contains(filter);
    }
    
    @Override
    public String getClipboardText() {
        return String.format("%s\t%s", entry.getConceptId(), entry.getName());
    }
}
