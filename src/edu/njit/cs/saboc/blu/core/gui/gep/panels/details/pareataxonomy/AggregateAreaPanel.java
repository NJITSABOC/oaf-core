
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.PartitionedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate.ContainerAggregatedNodePanel;
import java.util.Set;

/**
 *
 * @author Den
 */
public class AggregateAreaPanel extends PartitionedNodePanel {
    
    private final ContainerAggregatedNodePanel aggregatedNodeList;
    
    private final int aggregatedPAreaPanelIndex;
    
    public AggregateAreaPanel(PAreaTaxonomyConfiguration configuration) {
        
        super(new AggregateAreaDetailsPanel(configuration), configuration);
        
        this.aggregatedNodeList = new ContainerAggregatedNodePanel(configuration);
        
        aggregatedPAreaPanelIndex = super.addInformationTab(aggregatedNodeList, String.format("Aggregated Partial-areas in Area"));
    }
    
    
    @Override
    public void setContents(Node node) {
        super.setContents(node); 
        
        Area area = (Area)node;
        
        int aggregatePAreaCount = 0;
        
        Set<PArea> pareas = area.getPAreas();
        
        for(PArea parea : pareas) {
            AggregatePArea aggregatePArea = (AggregatePArea)parea;
            
            if(!aggregatePArea.getAggregatedNodes().isEmpty()) {
                aggregatePAreaCount++;
            }
        }
        
        if(aggregatePAreaCount > 0) {
            aggregatedNodeList.setContents(area);
            
            super.enableInformationTabAt(aggregatedPAreaPanelIndex, true);
        }
    }

    @Override
    public void clearContents() {
        super.clearContents(); 
        
        aggregatedNodeList.clearContents();
        
        super.enableInformationTabAt(aggregatedPAreaPanelIndex, false);
    }
    
}
