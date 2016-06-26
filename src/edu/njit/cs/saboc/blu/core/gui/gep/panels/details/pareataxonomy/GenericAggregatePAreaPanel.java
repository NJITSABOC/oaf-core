package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AggregatedNodesPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class GenericAggregatePAreaPanel <CONCEPT_T, AGGREGATEPAREA_T extends PArea & AggregateNode<CONCEPT_T, PAREA_T>, 
        PAREA_T extends PArea, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T>,
        CONFIG_T extends PAreaTaxonomyConfiguration> extends GenericPAreaPanel<CONCEPT_T, AGGREGATEPAREA_T, HIERARCHY_T, CONFIG_T> { 
    
    protected AggregatedNodesPanel<AGGREGATEPAREA_T, PAREA_T, CONCEPT_T> aggregateDetailsPanel;
    
    private int aggregateDetailsTabIndex;
    
    public GenericAggregatePAreaPanel(
            NodeDetailsPanel<AGGREGATEPAREA_T, CONCEPT_T> pareaDetailsPanel,
            NodeHierarchyPanel<CONCEPT_T, AGGREGATEPAREA_T, CONFIG_T> pareaHierarchyPanel,
            PAreaConceptHierarchyPanel<CONCEPT_T, AGGREGATEPAREA_T, HIERARCHY_T> conceptHierarchyPanel,
            AggregatedNodesPanel<AGGREGATEPAREA_T, PAREA_T, CONCEPT_T> aggregateDetailsPanel,
            CONFIG_T configuration) {

        super(pareaDetailsPanel, pareaHierarchyPanel, conceptHierarchyPanel, configuration);
        
        this.aggregateDetailsPanel = aggregateDetailsPanel;
        
        this.aggregateDetailsTabIndex = super.addGroupDetailsTab(aggregateDetailsPanel, String.format(
            "Aggregated %s", configuration.getTextConfiguration().getGroupTypeName(true)));
    }
    
    @Override
    public void setContents(AGGREGATEPAREA_T parea) {
        super.setContents(parea);

        AggregateNode<CONCEPT_T, PAREA_T> reducedPArea = (AggregateNode<CONCEPT_T, PAREA_T>)parea;
        
        if(reducedPArea.getAggregatedGroups().isEmpty()) {
            this.enableGroupDetailsTabAt(aggregateDetailsTabIndex, false);
            return;
        }
        
        this.aggregateDetailsPanel.setContents(parea);
        this.enableGroupDetailsTabAt(aggregateDetailsTabIndex, true);
    }

    @Override
    public void clearContents() {
        super.clearContents(); 
        
        aggregateDetailsPanel.clearContents();
        this.enableGroupDetailsTabAt(aggregateDetailsTabIndex, false);
    }
}
