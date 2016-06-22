package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;

/**
 *
 * @author Chris O
 */
public class PartitionedNodePanel extends NodeDashboardPanel {

    private final PartitionedNodeSubNodeList groupListPanel;

    public PartitionedNodePanel(
            NodeDetailsPanel containerDetailsPanel, 
            BLUPartitionedConfiguration configuration) {
        
        super(containerDetailsPanel, configuration);
        
        this.groupListPanel = new PartitionedNodeSubNodeList(configuration);
        
        String tabTitle = String.format("%s's %s", 
                configuration.getTextConfiguration().getContainerTypeName(false), 
                configuration.getTextConfiguration().getNodeTypeName(true));
        
        super.addGroupDetailsTab(groupListPanel, tabTitle);
    }

    @Override
    public void clearContents() {
        super.clearContents();
        
        groupListPanel.clearContents();
    }

    @Override
    public void setContents(Node node) {
        super.setContents(node);
        
        groupListPanel.setContents(node);
    }
}
