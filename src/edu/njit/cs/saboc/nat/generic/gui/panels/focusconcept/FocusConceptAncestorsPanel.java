package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;

/**
 * Panel for displaying the ancestors of the focus concept, in topological order
 * 
 * @author Chris O
 * @param <T>
 */
public class FocusConceptAncestorsPanel<T extends Concept> extends ConceptListPanel<T> {
    
    public FocusConceptAncestorsPanel(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource,
            boolean showFilter) {
        
        super(mainPanel, 
                dataSource, 
                CommonBrowserDataRetrievers.getTopologicalAncestorsRetriever(dataSource), 
                new SimpleConceptRenderer<>(dataSource, SimpleConceptRenderer.HierarchyDisplayInfo.None),
                showFilter,
                true);
    }
}