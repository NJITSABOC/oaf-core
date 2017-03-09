package edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.concept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer.HierarchyDisplayInfo;
import edu.njit.cs.saboc.nat.generic.gui.filterable.nestedlist.FilterableEntryPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Optional;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ConceptEntryPanel<T extends Concept> extends FilterableEntryPanel<Filterable<T>> {

    private final SimpleConceptRenderer<T> renderer;

    public ConceptEntryPanel(Filterable<T> entry, 
            Optional<String> filter, 
            ConceptBrowserDataSource<T> dataSource, 
            HierarchyDisplayInfo displayInfo) {
        
        super(entry, filter);
        
        this.renderer = new SimpleConceptRenderer<>(dataSource, displayInfo);
        this.renderer.showDetailsFor(entry);
        this.renderer.setBackground(Color.WHITE);
        
        this.setLayout(new BorderLayout());
        
        this.add(renderer, BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(-1, 50));
    }
}
