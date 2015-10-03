package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractAggregatedGroupsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class GenericAggregatePAreaPanel <CONCEPT_T, AGGREGATEPAREA_T extends GenericPArea & AggregateableConceptGroup<CONCEPT_T, PAREA_T>, 
        PAREA_T extends GenericPArea, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        CONFIG_T extends BLUGenericPAreaTaxonomyConfiguration> extends GenericPAreaPanel<CONCEPT_T, AGGREGATEPAREA_T, HIERARCHY_T, CONFIG_T> { 
    
    protected AbstractAggregatedGroupsPanel<AGGREGATEPAREA_T, PAREA_T, CONCEPT_T> aggregateDetailsPanel;
    
    private int aggregateDetailsTabIndex;
    
    public GenericAggregatePAreaPanel(
            AbstractNodeDetailsPanel<AGGREGATEPAREA_T, CONCEPT_T> pareaDetailsPanel,
            AbstractGroupHierarchyPanel<CONCEPT_T, AGGREGATEPAREA_T, CONFIG_T> pareaHierarchyPanel,
            PAreaConceptHierarchyPanel<CONCEPT_T, AGGREGATEPAREA_T, HIERARCHY_T> conceptHierarchyPanel,
            AbstractAggregatedGroupsPanel<AGGREGATEPAREA_T, PAREA_T, CONCEPT_T> aggregateDetailsPanel,
            CONFIG_T configuration) {

        super(pareaDetailsPanel, pareaHierarchyPanel, conceptHierarchyPanel, configuration);
        
        this.aggregateDetailsPanel = aggregateDetailsPanel;
        
        this.aggregateDetailsTabIndex = super.addGroupDetailsTab(aggregateDetailsPanel, String.format(
            "Aggregated %s", configuration.getTextConfiguration().getGroupTypeName(true)));
    }
    
    @Override
    public void setContents(AGGREGATEPAREA_T parea) {
        super.setContents(parea);

        AggregateableConceptGroup<CONCEPT_T, PAREA_T> reducedPArea = (AggregateableConceptGroup<CONCEPT_T, PAREA_T>)parea;
        
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
