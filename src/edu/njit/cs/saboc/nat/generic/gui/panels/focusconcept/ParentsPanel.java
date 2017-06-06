package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.FilterableParentEntry;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.ParentErrorDetailsRenderer;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu.ParentsRightClickMenuGenerator;

/**
 * Displays the parents of the focus concept
 * 
 * @author Chris O
 * @param <T>
 */
public class ParentsPanel<T extends Concept> extends ConceptListPanel<T> {
    
    public ParentsPanel(
            NATBrowserPanel<T> mainPanel, 
            boolean showFilter) {
        
        super(mainPanel, 
                CommonBrowserDataRetrievers.getParentsRetriever(mainPanel), 
                new ParentErrorDetailsRenderer<>(mainPanel),
                true,
                showFilter,
                true);
        
        this.setRightClickMenuGenerator(new ParentsRightClickMenuGenerator<>(mainPanel));
    }

    @Override
    protected Filterable<T> createFilterableEntry(T entry) {
        return new FilterableParentEntry<>(getMainPanel(), entry);
    }
}
