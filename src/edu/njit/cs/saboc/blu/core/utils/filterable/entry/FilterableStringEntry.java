package edu.njit.cs.saboc.blu.core.utils.filterable.entry;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;

/**
 *
 * @author harsh
 */
public class FilterableStringEntry extends Filterable<String> {
    private String entry;
    
    public FilterableStringEntry(String entry) {
        this.entry = entry;
    }
    
    public String getInitialText() {
        return String.format("<html>%s</html>", entry);
    }
    
    public String getFilterText(String filter) {
        return String.format("<html>%s</html>", filter(entry, filter));
    }

    @Override
    public String getObject() {
        return entry;
    }
}