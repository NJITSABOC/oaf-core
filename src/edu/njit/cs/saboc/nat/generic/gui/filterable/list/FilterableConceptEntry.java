package edu.njit.cs.saboc.nat.generic.gui.filterable.list;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;

/**
 * A filterable type for displaying a concept in a filterable list
 * 
 * @author Chris O
 * @param <T>
 */
public class FilterableConceptEntry<T extends Concept> extends Filterable<T> 
        implements NavigableEntry<T> {

    private final NATBrowserPanel<T> browserPanel;
    private final T concept;
    
    public FilterableConceptEntry(NATBrowserPanel<T> browserPanel, T concept) {
        this.browserPanel = browserPanel;
        this.concept = concept;
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
        
        if(browserPanel.getDataSource().isPresent()) {
            return browserPanel.getDataSource().get().getConceptToolTipText(concept);
        } else {
            return null;
        }
    }
}
