package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public abstract class AbstractAggregatedGroupsPanel extends AbNNodeInformationPanel {

    private final AbstractGroupList aggregateGroupList;
    private final AbstractEntityList<Concept> conceptList;
    
    private final JSplitPane splitPane;

    public AbstractAggregatedGroupsPanel(AbstractGroupList<SinglyRootedNode> groupList, 
            AbstractEntityList<Concept> conceptList, 
            BLUConfiguration configuration) {
        
        this.setLayout(new BorderLayout());
        
        this.aggregateGroupList = groupList;
        
        this.aggregateGroupList.addEntitySelectionListener(new EntitySelectionAdapter<GROUP_T>() {
            public void entityClicked(GROUP_T group) {
                conceptList.setContents(configuration.getDataConfiguration().getSortedConceptList(group));
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
        splitPane.setDividerLocation(300);
        
        AggregateNode<CONCEPT_T, GROUP_T> reducedGroup = (AggregateNode<CONCEPT_T, GROUP_T>)group;
        
        clearContents();
        
        if(reducedGroup.getAggregatedGroups().isEmpty()) {
            return;
        }
        
        ArrayList<GROUP_T> reducedGroups = getSortedAggregatedGroupList(reducedGroup.getAggregatedGroups());
        
        aggregateGroupList.setContents(reducedGroups); 
    }
    
    protected abstract ArrayList<GROUP_T> getSortedAggregatedGroupList(HashSet<GROUP_T> groups);
    
    @Override
    public void clearContents() {
        aggregateGroupList.clearContents();
        conceptList.clearContents();
    }
    
}
