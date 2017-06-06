
package edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.FilterableChildEntry;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.ChildErrorDetailsRenderer;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu.ChildrenRightClickMenu;

/**
 * Panel for displaying the children of the focus concept
 * 
 * @author Chris O
 * @param <T>
 */
public class ChildrenPanel<T extends Concept> extends ConceptListPanel<T> {
    
    public ChildrenPanel(NATBrowserPanel<T> mainPanel, 
            boolean showFilter) {
        
        super(mainPanel, 
                CommonBrowserDataRetrievers.getChildrenRetriever(mainPanel), 
                new ChildErrorDetailsRenderer<>(mainPanel),
                true,
                showFilter,
                true);
        
        this.setRightClickMenuGenerator(new ChildrenRightClickMenu<>(mainPanel));
    }

    @Override
    protected Filterable<T> createFilterableEntry(T entry) {
        return new FilterableChildEntry(getMainPanel(), entry);
    }
}
