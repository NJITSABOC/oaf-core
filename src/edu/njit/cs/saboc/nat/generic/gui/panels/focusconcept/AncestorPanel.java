package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.panels.MultiResultListPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AncestorPanel<T extends Concept> extends MultiResultListPanel<T>  {
        
    public AncestorPanel(NATBrowserPanel<T> browserPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(browserPanel, dataSource);
        
        super.addResultListPanel(new ParentsPanel<>(browserPanel, dataSource, true), "Parents Only");
        super.addResultListPanel(new GrandparentsPanel<>(browserPanel, dataSource), "Parents and Grandparents");
        super.addResultListPanel(new FocusConceptAncestorsPanel<>(browserPanel, dataSource, true), "All Ancestors");
    }
}
