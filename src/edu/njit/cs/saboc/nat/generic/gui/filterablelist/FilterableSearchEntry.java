package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import edu.njit.cs.saboc.nat.generic.data.BrowserSearchResult;
import edu.njit.cs.saboc.nat.generic.Options;

/**
 *
 * @author Chris
 */
public class FilterableSearchEntry extends Filterable<BrowserSearchResult> implements NavigableEntry {
    private BrowserSearchResult entry;

    public FilterableSearchEntry(BrowserSearchResult entry) {
        this.entry = entry;
    }
    
    public BrowserSearchResult getObject() {
        return entry;
    }

    public BrowserConcept getNavigableConcept() {
        return entry.getConcept();
    }

    public String getInitialText() {
        return String.format("<html>%s &nbsp;<font color='blue'>{%s}</font> " +
                "<font color='purple'>--%s</font>", entry.getName(),
                entry.getConcept().getName(), entry.getConcept().getId());
    }

    public String getFilterText(String filter) {
        return String.format("<html>%s &nbsp; <font color='blue'>{%s}</font> " +
                "<font color='purple'>--%s</font>", 
                filter(entry.getName(), filter),
                filter(entry.getConcept().getName(), filter),
                filter(entry.getConcept().getId(), filter));
    }
    
    public boolean containsFilter(String filter) {
        return entry.getName().toLowerCase().contains(filter) || 
                entry.getConcept().getName().toLowerCase().contains(filter) ||
                entry.getConcept().getId().contains(filter);
    }
}
