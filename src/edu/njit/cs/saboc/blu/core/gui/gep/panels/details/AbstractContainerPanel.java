package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUPartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ContainerConceptEntry;

/**
 *
 * @author Chris O
 */
public class AbstractContainerPanel<CONTAINER_T extends GenericGroupContainer, 
        GROUP_T extends GenericConceptGroup, CONCEPT_T, ENTRY_T extends ContainerConceptEntry<CONCEPT_T, GROUP_T>> extends AbstractNodePanel<CONTAINER_T, ENTRY_T> {

    protected final AbstractContainerGroupListPanel<CONTAINER_T, GROUP_T, CONCEPT_T> groupListPanel;

    public AbstractContainerPanel(
            AbstractNodeDetailsPanel<CONTAINER_T, ENTRY_T> containerDetailsPanel, 
            AbstractContainerGroupListPanel<CONTAINER_T, GROUP_T, CONCEPT_T> groupListPanel, 
            BLUPartitionedAbNConfiguration configuration) {
        
        super(containerDetailsPanel, configuration);
        
        this.groupListPanel = groupListPanel;
        
        String tabTitle = String.format("%s's %ss", configuration.getContainerTypeName(false), configuration.getGroupTypeName(true));
        
        super.addGroupDetailsTab(groupListPanel, tabTitle);
    }

    @Override
    public void clearContents() {
        super.clearContents();
        
        groupListPanel.clearContents();
    }

    @Override
    public void setContents(CONTAINER_T node) {
        super.setContents(node);
        
        groupListPanel.setContents(node);
    }
    
    
    @Override
    protected String getNodeTitle(CONTAINER_T node) {
        BLUPartitionedAbNConfiguration config = (BLUPartitionedAbNConfiguration)configuration;
        
        return config.getContainerName(node);
    }

    @Override
    protected String getNodeType() {
        BLUPartitionedAbNConfiguration config = (BLUPartitionedAbNConfiguration)configuration;
        
        return config.getContainerTypeName(false);
    }
}
