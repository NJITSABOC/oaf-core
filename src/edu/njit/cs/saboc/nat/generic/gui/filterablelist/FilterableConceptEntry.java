package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;

/**
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
    
    @Override
    public boolean containsFilter(String filter) {
        return concept.getName().toLowerCase().contains(filter) || concept.getIDAsString().toLowerCase().contains(filter);
    }
    
    @Override
    public String getClipboardText() {
        return String.format("%s\t%s", concept.getIDAsString(), concept.getName());
    }

    @Override
    public String getToolTipText() {
        return concept.getName();
    }
}
