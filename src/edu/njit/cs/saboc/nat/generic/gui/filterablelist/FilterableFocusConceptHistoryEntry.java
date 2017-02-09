package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.history.FocusConceptHistoryEntry;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class FilterableFocusConceptHistoryEntry<T extends Concept> extends Filterable<FocusConceptHistoryEntry<T>> 
        implements NavigableEntry<T> {

    private final FocusConceptHistoryEntry<T> historyEntry;
    private final ConceptBrowserDataSource<T> dataSource;

    public FilterableFocusConceptHistoryEntry(
            FocusConceptHistoryEntry<T> historyEntry, 
            ConceptBrowserDataSource<T> dataSource) {
        
        this.historyEntry = historyEntry;
        this.dataSource = dataSource;
    }

    @Override
    public FocusConceptHistoryEntry<T> getObject() {
        return historyEntry;
    }

    @Override
    public T getNavigableConcept() {
        return getObject().getConcept();
    }
    
    @Override
    public boolean containsFilter(String filter) {
        
        if(historyEntry.getConcept().getName().toLowerCase().contains(filter)) {
            return true;
        }
        
        if(historyEntry.getConcept().getIDAsString().toLowerCase().contains(filter)) {
            return true;
        }

        return false;
    }
    
    @Override
    public String getClipboardText() {
        return String.format("%s\t%s", 
                historyEntry.getConcept().getID(), 
                historyEntry.getConcept().getName());
    }

    @Override
    public String getToolTipText() {
        return historyEntry.getConcept().getName();
    }
}