package edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.auditset;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.dataretrievers.CommonBrowserDataRetrievers;

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
                new SimpleConceptRenderer(mainPanel, dataSource),
                true,
                true,
                false);
    }
    
    public void reloadAuditSet() {
        super.forceReload();
    }
}
