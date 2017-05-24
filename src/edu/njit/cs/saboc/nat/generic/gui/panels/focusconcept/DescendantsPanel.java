package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.MultiResultListPanel;

/**
 * Panel for displaying information about the descendants of the focus concept
 * 
 * @author Chris O
 * @param <T>
 */
public class DescendantsPanel<T extends Concept> extends MultiResultListPanel<T>  {
    
    public DescendantsPanel(NATBrowserPanel<T> browserPanel) {
        super(browserPanel);
        
        super.addResultListPanel(new ChildrenPanel<>(browserPanel, true), "Children Only");
        super.addResultListPanel(new GrandchildrenPanel<>(browserPanel), "Children and Grandchildren");
    }
}
