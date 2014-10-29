package edu.njit.cs.saboc.blu.core.utils.filterable.entry;

import SnomedShared.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;

/**
 *
 * @author harsh
 */
public class FilterableStringEntry extends Filterable {
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
    public Concept getNavigableConcept() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}