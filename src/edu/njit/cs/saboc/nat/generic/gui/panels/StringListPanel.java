package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.utils.filterable.entry.FilterableStringEntry;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class StringListPanel extends GenericResultListPanel<String> {

    public StringListPanel(final GenericNATBrowser mainPanel, NATDataField<ArrayList<String>> field, 
            ConceptBrowserDataSource dataSource, 
            DataLoadedListener<ArrayList<String>> dataLoadedListener, 
            boolean showFilter) {
        
        super(mainPanel, field, dataSource, dataLoadedListener, showFilter);
    }
    
    @Override
    protected Filterable<String> createFilterableEntry(String s) {
        return new FilterableStringEntry(s);
    }
}
