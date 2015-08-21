package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedAbNConfiguration;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public abstract class AbstractContainerGroupListPanel<CONTAINER_T extends GenericGroupContainer, GROUP_T extends GenericConceptGroup, CONCEPT_T>

        extends AbNNodeInformationPanel<CONTAINER_T> {
    
    private final JSplitPane splitPane;

    private final AbstractGroupList<GROUP_T> groupList;
    
    private final AbstractEntityList<CONCEPT_T> conceptList;
    
    protected final BLUPartitionedAbNConfiguration configuration;
    
    public AbstractContainerGroupListPanel(
            AbstractGroupList<GROUP_T> groupList, 
            AbstractEntityList<CONCEPT_T> conceptList, 
            BLUPartitionedAbNConfiguration configuration) {
        
        this.configuration = configuration;
        
        this.setLayout(new BorderLayout());

        this.splitPane = AbstractNodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        this.groupList = groupList;
        
        this.groupList.addEntitySelectionListener(new EntitySelectionListener<GROUP_T>() {
            public void entitySelected(GROUP_T group) {
                conceptList.setContents(getSortedConceptList(group));
            }
            
            public void noEntitySelected() {
                conceptList.clearContents();
            }
        });
        
        this.conceptList = conceptList;
        
        splitPane.setTopComponent(this.groupList );
        splitPane.setBottomComponent(this.conceptList);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    public abstract ArrayList<CONCEPT_T> getSortedConceptList(GROUP_T group); 

    @Override
    public void setContents(CONTAINER_T container) {
        
        splitPane.setDividerLocation(0.5);
        
        ArrayList<GROUP_T> sortedGroups = configuration.getSortedGroupList(container);
        
        groupList.setContents(sortedGroups);
        
        conceptList.clearContents();
    }

    @Override
    public void clearContents() {
        groupList.clearContents();
        conceptList.clearContents();
    }
}
