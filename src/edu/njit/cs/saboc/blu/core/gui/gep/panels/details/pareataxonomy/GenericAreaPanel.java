package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractContainerGroupListPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractContainerPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractDisjointAbNMetricsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.ContainerConceptEntry;

/**
 *
 * @author Chris O
 */
public class GenericAreaPanel<AREA_T extends GenericArea, 
        PAREA_T extends GenericPArea, CONCEPT_T> extends AbstractContainerPanel<AREA_T, PAREA_T, CONCEPT_T, ContainerConceptEntry<CONCEPT_T, PAREA_T>> {

    private final AbstractDisjointAbNMetricsPanel disjointMetricsPanel;
    
    private final int disjointMetricsTabIndex;
    
    public GenericAreaPanel(
           AbstractNodeDetailsPanel<AREA_T, ContainerConceptEntry<CONCEPT_T, PAREA_T>> containerDetailsPanel, 
           AbstractContainerGroupListPanel<AREA_T, PAREA_T, CONCEPT_T> groupListPanel, 
           AbstractDisjointAbNMetricsPanel disjointMetricsPanel,
           PAreaTaxonomyConfiguration configuration) {
        
        super(containerDetailsPanel, groupListPanel, configuration);
        
        this.disjointMetricsPanel = disjointMetricsPanel;
        
        this.disjointMetricsTabIndex = super.addGroupDetailsTab(disjointMetricsPanel, String.format(
            "Overlapping %s Metrics", configuration.getGroupTypeName(false)));
    }
    
    @Override
    public void setContents(AREA_T area) {
        super.setContents(area);
        
        
        
        if(area.hasOverlappingConcepts()) {
            this.enableGroupDetailsTabAt(disjointMetricsTabIndex, true);
        }
    }

    @Override
    public void clearContents() {
        super.clearContents(); 
        
        disjointMetricsPanel.clearContents();
        
        this.enableGroupDetailsTabAt(disjointMetricsTabIndex, false);
    }
    
}
