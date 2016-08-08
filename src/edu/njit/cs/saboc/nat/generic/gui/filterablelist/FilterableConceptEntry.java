package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

/**
 * A generic entry for a concept. It only displays its name and its unique ID
 * @author Chris
 */
public class FilterableConceptEntry extends Filterable<Concept> implements NavigableEntry<Concept> {

    /**
     * The concept
     */
    private final Concept concept;
    
    /**
     * The data source
     */
    private final ConceptBrowserDataSource dataSource;

    /**
     * 
     * @param concept
     * @param dataSource
     */
    public FilterableConceptEntry(Concept concept, 
            ConceptBrowserDataSource dataSource) {
        
        this.concept = concept;
        this.dataSource = dataSource;
    }

    /**
     * @return 
     */
    @Override
    public Concept getObject() {
        return concept;
    }

    /**
     * 
     * @return The concept
     */
    @Override
    public Concept getNavigableConcept() {
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
        return concept.getName().toLowerCase().contains(filter) || concept.getIDAsString().toLowerCase().contains(filter);
    }
    
    @Override
    public String getClipboardText() {
        return String.format("%s\t%s", concept.getIDAsString(), concept.getName());
    }
}
