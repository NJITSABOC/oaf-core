package edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer;

import edu.njit.cs.saboc.blu.core.utils.filterable.renderer.BaseFilterableRenderer;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.data.NATConceptSearchResult;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.FilterableConceptEntry;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JList;

/**
 * Renderer for displaying concept search results
 * 
 * @author Chris O
 * @param <T>
 */
public class SearchResultRenderer<T extends Concept> extends BaseFilterableRenderer<NATConceptSearchResult<T>> {
    
    private final SimpleConceptRenderer<T> renderer;
    
    public SearchResultRenderer(
            NATBrowserPanel<T> mainPanel,
            ConceptBrowserDataSource<T> dataSource) {
        
        this.renderer = new SimpleConceptRenderer<>(mainPanel, dataSource);

        this.setLayout(new BorderLayout());
        
        renderer.setOpaque(false);
        
        this.add(renderer, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Filterable<NATConceptSearchResult<T>>> list, 
            Filterable<NATConceptSearchResult<T>> value, 
            int index, 
            boolean isSelected, 
            boolean cellHasFocus) {
        
        super.getListCellRendererComponent(
                list, 
                value, 
                index, 
                isSelected, 
                cellHasFocus);

        showDetailsFor(value);
        
        return this;
    }

    @Override
    public void showDetailsFor(Filterable<NATConceptSearchResult<T>> element) {
        
        FilterableConceptEntry<T> conceptEntry = new FilterableConceptEntry<>(element.getObject().getConcept(), renderer.getDataSource());
        
        if(element.getCurrentFilter().isPresent()) {
            conceptEntry.setCurrentFilter(element.getCurrentFilter().get());
        }

        renderer.showDetailsFor(conceptEntry);
    }
}