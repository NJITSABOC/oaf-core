package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.auditset;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.FilterableAuditSetEntry;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.AuditSetConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu.AuditSetRightClickMenu;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AuditConceptList<T extends Concept> extends ConceptListPanel<T> {
    
    public AuditConceptList(
            NATBrowserPanel<T> mainPanel, 
            ConceptBrowserDataSource<T> dataSource) {
        
        super(mainPanel, 
                dataSource, 
                CommonBrowserDataRetrievers.getCurrentAuditSet(mainPanel), 
                new AuditSetConceptRenderer<>(mainPanel, dataSource),
                true,
                true,
                false);
        
        super.setRightClickMenuGenerator(new AuditSetRightClickMenu<>(mainPanel, dataSource));
    }
    
    @Override
    protected Filterable<T> createFilterableEntry(T entry) {
        return new FilterableAuditSetEntry<>(getMainPanel(), getDataSource(), entry);
    }
    
    public void reloadAuditSet() {
        super.forceReload();
    }
}
