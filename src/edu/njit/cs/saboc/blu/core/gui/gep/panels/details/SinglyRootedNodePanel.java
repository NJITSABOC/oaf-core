package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeTypeNameFactory;


/**
 *
 * @author Chris O
 */
public class SinglyRootedNodePanel extends NodeDashboardPanel {

    private final NodeHierarchyPanel groupHierarchyPanel;

    public SinglyRootedNodePanel(
            NodeDetailsPanel groupDetailsPanel, 
            NodeHierarchyPanel groupHierarchyPanel, 
            BLUConfiguration configuration,
            NodeTypeNameFactory nodeTypeName) {
        
        super(groupDetailsPanel, configuration, nodeTypeName);
        
        this.groupHierarchyPanel = groupHierarchyPanel;

        addGroupDetailsTab(groupHierarchyPanel, String.format("%s Hierarchy", nodeTypeName.getNodeTypeName(false)));
    }
}
