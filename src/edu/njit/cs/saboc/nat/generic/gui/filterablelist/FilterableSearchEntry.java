package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.BrowserSearchResult;

/**
 * A filterable entry for a search result list
 * @author Chris
 */
public class FilterableSearchEntry extends Filterable<BrowserSearchResult> implements NavigableEntry<Concept> {
   
    /**
     * The search result
     */
    private BrowserSearchResult entry;

    /**
     * 
     * @param entry The search result
     */
    public FilterableSearchEntry(BrowserSearchResult entry) {
        this.entry = entry;
    }
    
    /**
     * 
     * @return The search result
     */
    public BrowserSearchResult getObject() {
        return entry;
    }

    /**
     * 
     * @return The search result concept
     */
    public Concept getNavigableConcept() {
        return entry.getConcept();
    }

    public String getInitialText() {
        return String.format("<html>%s &nbsp;<font color='blue'>{%s}</font>", 
                entry.getConcept().getName(),
                entry.getConcept().getIDAsString());
    }
    
    public String getInitialText(boolean showURIs) {
        if (showURIs) {
            return getInitialText();
        } else {
            return String.format("<html>%s", entry.getConcept().getName());
        }
    }

    public String getFilterText(String filter) {
        return String.format("<html>%s &nbsp; <font color='purple'>--%s</font>", 
                filter(entry.getName(), filter),
                filter(entry.getConceptId(), filter));
    }
    
    public String getFilterText(String filter, boolean showURIs) {
        if (showURIs) {
            return getFilterText(filter);
        } else {
            return String.format("<html>%s", filter(entry.getName(), filter));
        }
    }
    /**
     * 
     * @param filter The filter
     * @return True if the search result's term or search result concept's unique ID contains the filter
     */
    public boolean containsFilter(String filter) {
        return 
                entry.getName().toLowerCase().contains(filter) || 
                entry.getConceptId().contains(filter);
    }
    
    @Override
    public String getClipboardText() {
        return String.format("%s\t%s", entry.getConcept().getIDAsString(), entry.getConcept().getName());
    }
}
