package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.BrowserNavigableFilterableList;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.FilterableConceptEntry;
import edu.njit.cs.saboc.nat.generic.gui.listeners.ConceptListNavigateSelectionAction;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class ConceptListPanel<T> extends GenericResultListPanel<T, T> {

    public ConceptListPanel(
            GenericNATBrowser<T> mainPanel, 
            NATDataField<T, ArrayList<T>> field, 
            ConceptBrowserDataSource<T> dataSource, 
            DataLoadedListener<ArrayList<T>> listener,
            boolean showFilter) {
        
        super(mainPanel, 
                new BrowserNavigableFilterableList(
                        mainPanel.getFocusConcept(), 
                        mainPanel.getOptions(),
                        new ConceptListNavigateSelectionAction(mainPanel.getFocusConcept())), 
                
                field, dataSource,
                listener, showFilter);
    }
    
    protected Filterable<T> createFilterableEntry(T item) {
        return new FilterableConceptEntry<T>(item, dataSource);
    }
}
