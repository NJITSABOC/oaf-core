package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducingGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractAggregatedGroupsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class GenericAggregatePAreaPanel <CONCEPT_T, AGGREGATEPAREA_T extends GenericPArea & ReducingGroup<CONCEPT_T, PAREA_T>, PAREA_T extends GenericPArea> 
    extends AbstractGroupPanel<AGGREGATEPAREA_T, CONCEPT_T> { 
    
    protected AbstractAggregatedGroupsPanel<AGGREGATEPAREA_T, PAREA_T, CONCEPT_T> aggregateDetailsPanel;
    
    private int aggregateDetailsTabIndex;
    
    public GenericAggregatePAreaPanel(
            AbstractNodeDetailsPanel<AGGREGATEPAREA_T, CONCEPT_T> pareaDetailsPanel,
            AbstractGroupHierarchyPanel<CONCEPT_T, AGGREGATEPAREA_T> pareaHierarchyPanel,
            AbstractAggregatedGroupsPanel<AGGREGATEPAREA_T, PAREA_T, CONCEPT_T> aggregateDetailsPanel,
            PAreaTaxonomyConfiguration configuration) {

        super(pareaDetailsPanel, pareaHierarchyPanel, configuration);
        
        this.aggregateDetailsPanel = aggregateDetailsPanel;
        
        this.aggregateDetailsTabIndex = super.addGroupDetailsTab(aggregateDetailsPanel, String.format(
            "Aggregate %s", configuration.getGroupTypeName(false)));
    }
    
    @Override
    public void setContents(AGGREGATEPAREA_T parea) {
        super.setContents(parea);

        ReducingGroup<CONCEPT_T, PAREA_T> reducedPArea = (ReducingGroup<CONCEPT_T, PAREA_T>)parea;
        
        if(reducedPArea.getReducedGroups().isEmpty()) {
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
