package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;


/**
 *
 * @author Chris O
 */
public class SinglyRootedNodePanel extends NodeDashboardPanel {

    private final NodeHierarchyPanel groupHierarchyPanel;
    
    private final ConceptHierarchyPanel conceptHierarchyPanel;

    public SinglyRootedNodePanel(
            NodeDetailsPanel groupDetailsPanel, 
            NodeHierarchyPanel groupHierarchyPanel, 
            ConceptHierarchyPanel conceptHierarchyPanel,
            AbNConfiguration configuration) {
        
        super(groupDetailsPanel, configuration);
        
        this.groupHierarchyPanel = groupHierarchyPanel;

        addGroupDetailsTab(groupHierarchyPanel, String.format("%s Hierarchy", 
                configuration.getTextConfiguration().getNodeTypeName(false)));
        
        this.conceptHierarchyPanel = conceptHierarchyPanel;
        
        addGroupDetailsTab(groupHierarchyPanel, String.format("%s's %s Hierarchy", 
                configuration.getTextConfiguration().getNodeTypeName(false),
                configuration.getTextConfiguration().getConceptTypeName(false)));
    }
}
