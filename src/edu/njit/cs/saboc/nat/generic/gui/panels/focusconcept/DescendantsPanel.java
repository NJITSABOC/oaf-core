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
public class DescendantsPanel<T extends Concept> extends MultiResultListPanel<T>  {
    
    public DescendantsPanel(NATBrowserPanel<T> browserPanel, ConceptBrowserDataSource<T> dataSource) {
        super(browserPanel, dataSource);
        
        super.addResultListPanel(new ChildrenPanel<>(browserPanel, dataSource, true), "Children Only");
        super.addResultListPanel(new GrandchildrenPanel<>(browserPanel, dataSource), "Children and Grandchildren");
    }
}
