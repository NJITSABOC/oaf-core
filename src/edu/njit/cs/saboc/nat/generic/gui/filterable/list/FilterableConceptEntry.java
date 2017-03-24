package edu.njit.cs.saboc.nat.generic.gui.filterable.list;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

/**
 * A filterable type for displaying a concept in a filterable list
 * 
 * @author Chris O
 * @param <T>
 */
public class FilterableConceptEntry<T extends Concept> extends Filterable<T> 
        implements NavigableEntry<T> {

    private final T concept;
    
    private final ConceptBrowserDataSource<T> dataSource;

    public FilterableConceptEntry(T concept, 
            ConceptBrowserDataSource<T> dataSource) {
        
        this.concept = concept;
        this.dataSource = dataSource;
    }

    @Override
    public T getObject() {
        return concept;
    }

    @Override
    public T getNavigableConcept() {
        return getObject();
    }
    
    /**
     * A concept is included if the name or id includes the given filter
     * 
     * @param filter
     * @return 
     */
    @Override
    public boolean containsFilter(String filter) {
        return concept.getName().toLowerCase().contains(filter) || 
                concept.getIDAsString().toLowerCase().contains(filter);
    }
    
    @Override
    public String getClipboardText() {
        return String.format("%s\t%s", concept.getIDAsString(), concept.getName());
    }

    @Override
    public String getToolTipText() {
        return dataSource.getConceptToolTipText(concept);
    }
}
