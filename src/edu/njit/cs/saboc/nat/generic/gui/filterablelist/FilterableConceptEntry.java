package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

/**
 * A generic entry for a concept. It only displays its name and its unique ID
 * @author Chris
 */
public class FilterableConceptEntry<T> extends Filterable<T> implements NavigableEntry<T> {

    /**
     * The concept
     */
    private final T concept;
    
    /**
     * The data source
     */
    private final ConceptBrowserDataSource<T> dataSource;

    /**
     * 
     * @param concept The concept
     * @param dataSource The data source
     */
    public FilterableConceptEntry(T concept, ConceptBrowserDataSource<T> dataSource) {
        this.concept = concept;
        this.dataSource = dataSource;
    }

    /**
     * @return The concept  
     */
    public T getObject() {
        return concept;
    }

    /**
     * 
     * @return The concept
     */
    public T getNavigableConcept() {
        return getObject();
    }

    /**
     * 
     * @param conceptName The name of the concept
     * @param conceptId The unique ID of the concept
     * @return A styled string with the name and unique id
     */
    protected String createEntryStr(String conceptName, String conceptId) {
        return String.format(
                "<html>%s <font color='blue'>(%s)</font>",
                conceptName, conceptId);
    }

    /**
     * 
     * @return The original unfiltered text
     */
    public String getInitialText() {
        return createEntryStr(dataSource.getConceptName(concept), dataSource.getConceptId(concept));
    }

    /**
     * 
     * @param filter The filter
     * @return The entry with the filtered portion highlighted 
     */
    public String getFilterText(String filter) {
        if (!filter.isEmpty()) {
            return createEntryStr(filter(dataSource.getConceptName(concept), filter), filter(dataSource.getConceptId(concept), filter));
        } else {
            return getInitialText();
        }
    }
    
    /**
     * 
     * @param filter The filter
     * @return True if the concept's name or id contains the filter, false otherwise
     */
    public boolean containsFilter(String filter) {
        return dataSource.getConceptName(concept).toLowerCase().contains(filter) || dataSource.getConceptId(concept).toLowerCase().contains(filter);
    }
    
    @Override
    public String getClipboardText() {
        return String.format("%s\t%s", dataSource.getConceptId(concept), dataSource.getConceptName(concept));
    }
}
