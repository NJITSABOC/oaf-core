package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.FilterableConceptEntry;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ConceptListPanel<T extends Concept> extends ResultListPanel<T, T> {

    public ConceptListPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource,
            DataRetriever<T, ArrayList<T>> dataRetriever,
            boolean showFilter,
            boolean showBorder) {
        
        super(mainPanel, 
                dataSource,
                dataRetriever,
                new SimpleConceptRenderer<>(),
                showFilter,
                showBorder);
        
        super.addResultSelectedListener(new ResultSelectedListener<T>() {

            @Override
            public void resultSelected(T result) {
                mainPanel.getFocusConceptManager().navigateTo(result);
            }

            @Override
            public void noResultSelected() {
                
            }
        });
    }

    @Override
    protected Filterable<T> createFilterableEntry(T entry) {
        return new FilterableConceptEntry(entry, this.getDataSource());
    }
}
