package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class DiffPartitionedNodePanel<T extends PartitionedNode> extends NodeDashboardPanel<T> {
    
    private final DiffNodeChangesPanel<T> diffNodeChangesPanel;
    
    private final DiffPartitionedNodeSubNodeList groupListPanel;
    
    public DiffPartitionedNodePanel(
            NodeDetailsPanel<T> diffContainerDetailsPanel,
            PartitionedAbNConfiguration configuration) {
        
        super(diffContainerDetailsPanel, configuration);
        
        this.diffNodeChangesPanel = new DiffNodeChangesPanel<>(configuration);
        
        addInformationTab(diffNodeChangesPanel, String.format("%s Changes", 
                configuration.getTextConfiguration().getContainerTypeName(false)));
        
        this.groupListPanel = new DiffPartitionedNodeSubNodeList(configuration);
        
        String subnodeListTabTitle = String.format("%s's %s", 
                configuration.getTextConfiguration().getContainerTypeName(false), 
                configuration.getTextConfiguration().getNodeTypeName(true));
        
        super.addInformationTab(groupListPanel, subnodeListTabTitle);
    }
}
