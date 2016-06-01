package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractContainerGroupListPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractContainerPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractDisjointAbNMetricsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.ContainerConceptEntry;

/**
 *
 * @author Chris O
 */
public class GenericAreaPanel<AREA_T extends Area, 
        PAREA_T extends PArea, 
        CONCEPT_T,
        CONFIG_T extends BLUGenericPAreaTaxonomyConfiguration> extends 
        
            AbstractContainerPanel<AREA_T, PAREA_T, CONCEPT_T, ContainerConceptEntry<CONCEPT_T, PAREA_T>, CONFIG_T> {

    private final AbstractDisjointAbNMetricsPanel disjointMetricsPanel;
    
    private final int disjointMetricsTabIndex;
    
    public GenericAreaPanel(
           AbstractNodeDetailsPanel<AREA_T, ContainerConceptEntry<CONCEPT_T, PAREA_T>> containerDetailsPanel, 
           AbstractContainerGroupListPanel<AREA_T, PAREA_T, CONCEPT_T> groupListPanel, 
           AbstractDisjointAbNMetricsPanel disjointMetricsPanel,
           CONFIG_T configuration) {
        
        super(containerDetailsPanel, groupListPanel, configuration);
        
        this.disjointMetricsPanel = disjointMetricsPanel;
        
        this.disjointMetricsTabIndex = super.addGroupDetailsTab(disjointMetricsPanel, String.format(
            "Overlapping %s Metrics", configuration.getTextConfiguration().getGroupTypeName(false)));
    }
    
    @Override
    public void setContents(AREA_T area) {
        super.setContents(area);
        
        BLUGenericPAreaTaxonomyConfiguration config = getConfiguration();
        
        if(!config.getDataConfiguration().getContainerOverlappingResults(area).isEmpty()) {
            disjointMetricsPanel.setContents(area);
            
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
