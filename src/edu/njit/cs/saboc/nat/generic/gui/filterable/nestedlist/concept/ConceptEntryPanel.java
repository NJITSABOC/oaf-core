package edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.concept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.blu.core.utils.filterable.renderer.BaseFilterableRenderer;
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.FilterableEntryPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Optional;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ConceptEntryPanel<T extends Concept> extends FilterableEntryPanel<Filterable<T>> {

    private final BaseFilterableRenderer<T> renderer;

    public ConceptEntryPanel(
            NATBrowserPanel<T> mainPanel,
            Filterable<T> entry, 
            Optional<String> filter, 
            BaseFilterableRenderer<T> renderer) {
        
        super(entry, filter);
        
        this.renderer = renderer;
        
        this.renderer.showDetailsFor(entry);
        this.renderer.setBackground(Color.WHITE);
        
        this.setLayout(new BorderLayout());
        
        this.add(renderer, BorderLayout.CENTER);
    }
}
