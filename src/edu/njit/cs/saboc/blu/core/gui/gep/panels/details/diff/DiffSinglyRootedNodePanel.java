package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;

/**
 *
 * @author Chris O
 */
public class DiffSinglyRootedNodePanel<T extends Node> extends NodeDashboardPanel<T> {

    private final NodeHierarchyPanel<T> groupHierarchyPanel;
    
    private final DiffNodeChangesPanel<T> diffNodeChangesPanel;
    
    public DiffSinglyRootedNodePanel(
            NodeDetailsPanel<T> groupDetailsPanel, 
            NodeHierarchyPanel<T> groupHierarchyPanel, 
            AbNConfiguration configuration) {
        
        super(groupDetailsPanel, configuration);
        
        this.groupHierarchyPanel = groupHierarchyPanel;

        addInformationTab(groupHierarchyPanel, String.format("%s Hierarchy", 
                configuration.getTextConfiguration().getNodeTypeName(false)));
        
        this.diffNodeChangesPanel = new DiffNodeChangesPanel<>(configuration);
        
        addInformationTab(diffNodeChangesPanel, String.format("%s Changes", 
                configuration.getTextConfiguration().getNodeTypeName(false)));
    }
}