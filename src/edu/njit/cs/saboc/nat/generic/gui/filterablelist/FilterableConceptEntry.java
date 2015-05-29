package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

/**
 *
 * @author Chris
 */
public class FilterableConceptEntry<T> extends Filterable<T> implements NavigableEntry<T> {

    private final T concept;
    
    private final ConceptBrowserDataSource<T> dataSource;

    public FilterableConceptEntry(T c, ConceptBrowserDataSource<T> dataSource) {
        this.concept = c;
        this.dataSource = dataSource;
    }

    public T getObject() {
        return concept;
    }

    public T getNavigableConcept() {
        return getObject();
    }

    protected String createEntryStr(String conceptName, String conceptId) {
        return String.format(
                "<html>%s <font color='blue'>(%s)</font>",
                conceptName, conceptId);
    }

    public String getInitialText() {
        return createEntryStr(dataSource.getConceptName(concept), dataSource.getConceptId(concept));
    }

    public String getFilterText(String filter) {
        if (!filter.isEmpty()) {
            return createEntryStr(filter(dataSource.getConceptName(concept), filter), filter(dataSource.getConceptId(concept), filter));
        } else {
            return getInitialText();
        }
    }
    
    public boolean containsFilter(String filter) {
        return dataSource.getConceptName(concept).toLowerCase().contains(filter) || dataSource.getConceptId(concept).toLowerCase().contains(filter);
    }
}
