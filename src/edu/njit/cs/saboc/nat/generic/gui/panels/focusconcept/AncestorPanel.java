package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.MultiResultListPanel;

/**
 * Panel for displaying results related to the ancestors of the focus concept
 * 
 * @author Chris O
 * @param <T>
 */
public class AncestorPanel<T extends Concept> extends MultiResultListPanel<T>  {
        
    public AncestorPanel(NATBrowserPanel<T> browserPanel) {
        
        super(browserPanel);
        
        super.addResultListPanel(new ParentsPanel<>(browserPanel, true), "Parents Only");
        super.addResultListPanel(new GrandparentsPanel<>(browserPanel), "Parents and Grandparents");
        super.addResultListPanel(new FocusConceptAncestorsPanel<>(browserPanel, true), "All Ancestors");
    }
}
