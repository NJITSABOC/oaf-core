package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.GenericNATBrowser;
import edu.njit.cs.saboc.nat.generic.fields.NATDataField;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.BrowserNavigableFilterableList;
import edu.njit.cs.saboc.nat.generic.gui.filterablelist.FilterableConceptEntry;
import edu.njit.cs.saboc.nat.generic.gui.listeners.ConceptListNavigateSelectionAction;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class ConceptListPanel extends GenericResultListPanel<Concept> {

    public ConceptListPanel(
            GenericNATBrowser mainPanel, 
            NATDataField<ArrayList<Concept>> field, 
            ConceptBrowserDataSource dataSource,
            boolean showFilter) {
        
        super(mainPanel, 
                new BrowserNavigableFilterableList(mainPanel,  new ConceptListNavigateSelectionAction(mainPanel.getFocusConcept())), 
                field, dataSource,showFilter);
    }
    
    protected Filterable<Concept> createFilterableEntry(Concept item) {
        return new FilterableConceptEntry(item, dataSource);
    }
}
