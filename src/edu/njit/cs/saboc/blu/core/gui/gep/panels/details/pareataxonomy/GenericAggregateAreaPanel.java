
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.reduced.AggregateableConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractContainerGroupListPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractContainerPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeDetailsPanel;
import java.util.ArrayList;

/**
 *
 * @author Den
 */
public class GenericAggregateAreaPanel<AREA_T extends GenericArea, 
        PAREA_T extends GenericPArea,
        AGGREGATEPAREA_T extends GenericPArea & AggregateableConceptGroup<CONCEPT_T, PAREA_T>, 
        CONCEPT_T,
        ENTRY_T>
            extends AbstractContainerPanel<AREA_T, AGGREGATEPAREA_T, CONCEPT_T, ENTRY_T> {
    
    private final GenericAreaAggregatedPAreaPanel<CONCEPT_T, AREA_T, PAREA_T, AGGREGATEPAREA_T> aggregatedPAreaPanel;
    
    private final int aggregatedPAreaPanelIndex;
    
    public GenericAggregateAreaPanel(
           AbstractNodeDetailsPanel<AREA_T, ENTRY_T> containerDetailsPanel, 
           AbstractContainerGroupListPanel<AREA_T, AGGREGATEPAREA_T, CONCEPT_T> groupListPanel, 
           GenericAreaAggregatedPAreaPanel<CONCEPT_T, AREA_T, PAREA_T, AGGREGATEPAREA_T> aggregatedPAreaPanel,
           PAreaTaxonomyConfiguration configuration) {
        
        super(containerDetailsPanel, groupListPanel, configuration);
        
        this.aggregatedPAreaPanel = aggregatedPAreaPanel;
        
        aggregatedPAreaPanelIndex = super.addGroupDetailsTab(aggregatedPAreaPanel, String.format("Aggregated Partial-areas in Area"));
    }
    
    
    @Override
    public void setContents(AREA_T area) {
        super.setContents(area); 
        
        int aggregatePAreaCount = 0;
        
        ArrayList<AGGREGATEPAREA_T> pareas = area.getAllPAreas();
        
        for(AGGREGATEPAREA_T parea : pareas) {
            AggregateableConceptGroup<CONCEPT_T, PAREA_T> aggregatePArea = (AggregateableConceptGroup<CONCEPT_T, PAREA_T>)parea;
            
            if(!aggregatePArea.getAggregatedGroups().isEmpty()) {
                aggregatePAreaCount++;
            }
        }
        
        if(aggregatePAreaCount > 0) {
            aggregatedPAreaPanel.setContents(area);
            
            super.enableGroupDetailsTabAt(aggregatedPAreaPanelIndex, true);
        }
    }

    @Override
    public void clearContents() {
        super.clearContents(); 
        
        aggregatedPAreaPanel.clearContents();
        
        super.enableGroupDetailsTabAt(aggregatedPAreaPanelIndex, false);
    }
    
}
