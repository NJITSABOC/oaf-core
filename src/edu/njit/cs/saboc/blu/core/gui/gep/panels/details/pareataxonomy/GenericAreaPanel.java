package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.PartitionedNodeSubNodeList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.PartitionedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.DisjointAbNMetricsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.PartitionedNodeConceptEntry;

/**
 *
 * @author Chris O
 */
public class GenericAreaPanel<AREA_T extends Area, 
        PAREA_T extends PArea, 
        CONCEPT_T,
        CONFIG_T extends BLUGenericPAreaTaxonomyConfiguration> extends 
        
            PartitionedNodePanel<AREA_T, PAREA_T, CONCEPT_T, PartitionedNodeConceptEntry<CONCEPT_T, PAREA_T>, CONFIG_T> {

    private final DisjointAbNMetricsPanel disjointMetricsPanel;
    
    private final int disjointMetricsTabIndex;
    
    public GenericAreaPanel(
           NodeDetailsPanel<AREA_T, PartitionedNodeConceptEntry<CONCEPT_T, PAREA_T>> containerDetailsPanel, 
           PartitionedNodeSubNodeList<AREA_T, PAREA_T, CONCEPT_T> groupListPanel, 
           DisjointAbNMetricsPanel disjointMetricsPanel,
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
