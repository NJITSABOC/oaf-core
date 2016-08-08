package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.utils.filterable.entry.FilterableStringEntry;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class StringListPanel extends GenericResultListPanel<String> {

    public StringListPanel(
            GenericNATBrowser mainPanel, 
            NATDataField<ArrayList<String>> field, 
            ConceptBrowserDataSource dataSource, 
            boolean showFilter) {
        
        super(mainPanel, field, dataSource, showFilter);
    }
    
    @Override
    protected Filterable<String> createFilterableEntry(String s) {
        return new FilterableStringEntry(s);
    }
}
