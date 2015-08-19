package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducingGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public abstract class AbstractAggregatedGroupsPanel<AGGREGATEGROUP_T extends GenericConceptGroup & ReducingGroup<CONCEPT_T, GROUP_T>, 
        GROUP_T extends GenericConceptGroup, CONCEPT_T> extends AbNNodeInformationPanel<AGGREGATEGROUP_T> {

    private final AbstractGroupList<GROUP_T> aggregateGroupList;
    private final AbstractEntityList<CONCEPT_T> conceptList;
    
    private final JSplitPane splitPane;

    public AbstractAggregatedGroupsPanel(AbstractGroupList<GROUP_T> groupList, 
            AbstractEntityList<CONCEPT_T> conceptList, 
            BLUAbNConfiguration configuration) {
        
        this.setLayout(new BorderLayout());
        
        this.aggregateGroupList = groupList;
        
        this.aggregateGroupList.addEntitySelectionListener(new EntitySelectionListener<GROUP_T>() {
            public void entitySelected(GROUP_T group) {
                conceptList.setContents(configuration.getSortedConceptList(group));
            }
            
            public void noEntitySelected() {
                conceptList.clearContents();
            }
        });
        
        this.conceptList = conceptList;
        
        this.splitPane = AbstractNodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        splitPane.setTopComponent(this.aggregateGroupList);
        splitPane.setBottomComponent(this.conceptList);
        
        this.add(splitPane, BorderLayout.CENTER);
    }
    
    @Override
    public void setContents(AGGREGATEGROUP_T group) {
        splitPane.setDividerLocation(0.5);
        
        ReducingGroup<CONCEPT_T, GROUP_T> reducedGroup = (ReducingGroup<CONCEPT_T, GROUP_T>)group;
        
        clearContents();
        
        if(reducedGroup.getReducedGroups().isEmpty()) {
            return;
        }
        
        ArrayList<GROUP_T> reducedGroups = getSortedAggregatedGroupList(reducedGroup.getReducedGroups());
        
        aggregateGroupList.setContents(reducedGroups); 
    }
    
    protected abstract ArrayList<GROUP_T> getSortedAggregatedGroupList(HashSet<GROUP_T> groups);
    
    @Override
    public void clearContents() {
        aggregateGroupList.clearContents();
        conceptList.clearContents();
    }
    
}
