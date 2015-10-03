package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;

/**
 *
 * @author Chris O
 */
public class AbstractContainerPanel<CONTAINER_T extends GenericGroupContainer, 
        GROUP_T extends GenericConceptGroup, CONCEPT_T, 
        ENTRY_T,
        CONFIG_T extends BLUPartitionedConfiguration> extends AbstractNodePanel<CONTAINER_T, ENTRY_T, CONFIG_T> {

    protected final AbstractContainerGroupListPanel<CONTAINER_T, GROUP_T, CONCEPT_T> groupListPanel;

    public AbstractContainerPanel(
            AbstractNodeDetailsPanel<CONTAINER_T, ENTRY_T> containerDetailsPanel, 
            AbstractContainerGroupListPanel<CONTAINER_T, GROUP_T, CONCEPT_T> groupListPanel, 
            CONFIG_T configuration) {
        
        super(containerDetailsPanel, configuration);
        
        this.groupListPanel = groupListPanel;
        
        String tabTitle = String.format("%s's %s", 
                configuration.getTextConfiguration().getContainerTypeName(false), 
                configuration.getTextConfiguration().getGroupTypeName(true));
        
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
        CONFIG_T config = getConfiguration();
        
        return config.getTextConfiguration().getContainerName(node);
    }

    @Override
    protected String getNodeType() {
        CONFIG_T config = getConfiguration();
        
        return config.getTextConfiguration().getContainerTypeName(false);
    }
}
