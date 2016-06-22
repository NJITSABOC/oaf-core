package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public abstract class AbstractContainerGroupListPanel<
        CONTAINER_T extends GenericGroupContainer, 
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T>

        extends BaseNodeInformationPanel<CONTAINER_T> {
    
    private final JSplitPane splitPane;

    private final NodeList<GROUP_T> groupList;
    
    private final AbstractEntityList<CONCEPT_T> conceptList;
    
    protected final BLUConfiguration configuration;
    
    public AbstractContainerGroupListPanel(
            NodeList<GROUP_T> groupList, 
            AbstractEntityList<CONCEPT_T> conceptList, 
            BLUPartitionedConfiguration configuration) {
        
        this.configuration = configuration;
        
        this.setLayout(new BorderLayout());

        this.splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        this.groupList = groupList;
        
        this.groupList.addEntitySelectionListener(new EntitySelectionAdapter<GROUP_T>() {
            public void entityClicked(GROUP_T group) {
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
    
    protected NodeList<GROUP_T> getGroupList() {
        return groupList;
    }
    
    public abstract ArrayList<CONCEPT_T> getSortedConceptList(GROUP_T group); 

    @Override
    public void setContents(CONTAINER_T container) {
        
        splitPane.setDividerLocation(300);
        
        ArrayList<GROUP_T> sortedGroups = configuration.getDataConfiguration().getSortedGroupList(container);
        
        groupList.setContents(sortedGroups);
        
        conceptList.clearContents();
    }

    @Override
    public void clearContents() {
        groupList.clearContents();
        conceptList.clearContents();
    }
}
