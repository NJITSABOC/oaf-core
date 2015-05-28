package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.BrowserNavigableFilterableList;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.FilterableConceptEntry;
import edu.njit.cs.saboc.nat.generic.gui.listeners.DataLoadedListener;
import edu.njit.cs.saboc.nat.generic.gui.listeners.ConceptListNavigateSelectionAction;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class ConceptListPanel extends GenericResultListPanel<BrowserConcept> {

    public ConceptListPanel(
            GenericNATBrowser mainPanel, 
            NATDataField<ArrayList<BrowserConcept>> field, 
            ConceptBrowserDataSource dataSource, 
            DataLoadedListener<ArrayList<BrowserConcept>> listener,
            boolean showFilter) {
        
        super(mainPanel, 
                new BrowserNavigableFilterableList(mainPanel.getFocusConcept(), 
                        mainPanel.getOptions(),
                        new ConceptListNavigateSelectionAction(mainPanel.getFocusConcept())), 
                
                field, dataSource,
                listener, showFilter);
    }
        
    @Override
    protected Filterable<BrowserConcept> createFilterableEntry(BrowserConcept c) {
        return new FilterableConceptEntry(c);
    }
}
