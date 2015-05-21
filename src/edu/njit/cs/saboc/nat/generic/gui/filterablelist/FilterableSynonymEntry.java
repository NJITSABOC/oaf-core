package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.utils.filterable.entry.FilterableStringEntry;

/**
 *
 * @author Chris
 */
public class FilterableSynonymEntry extends FilterableStringEntry {
    private String synonym;

    public FilterableSynonymEntry(String synonym) {
        super(synonym);
    }
}
