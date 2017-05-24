package edu.njit.cs.saboc.nat.generic.gui.panels;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.FilterableConceptEntry;
import edu.njit.cs.saboc.blu.core.utils.filterable.renderer.BaseFilterableRenderer;
import java.util.ArrayList;

/**
 * A result list that displays some list of concepts (e.g., parents, children, siblings)
 * 
 * @author Chris O
 * @param <T>
 */
public class ConceptListPanel<T extends Concept> extends ResultListPanel<T, T> {

    public ConceptListPanel(
            NATBrowserPanel<T> mainPanel, 
            DataRetriever<T, ArrayList<T>> dataRetriever,
            BaseFilterableRenderer<T> renderer,
            boolean navigable,
            boolean showFilter,
            boolean showBorder) {
        
        super(mainPanel, 
                dataRetriever,
                renderer,
                showFilter,
                showBorder);
        
        if (navigable) {
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
    }

    @Override
    protected Filterable<T> createFilterableEntry(T entry) {
        return new FilterableConceptEntry(getMainPanel(), entry);
    }

    @Override
    public void reset() {
        this.dataPending();
    }
}
