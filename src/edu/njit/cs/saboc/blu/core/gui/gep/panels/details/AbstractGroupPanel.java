package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class AbstractGroupPanel<GROUP_T extends GenericConceptGroup, CONCEPT_T> extends AbstractNodePanel<GROUP_T, CONCEPT_T> {

    private final AbstractGroupHierarchyPanel<CONCEPT_T, GROUP_T> groupHierarchyPanel;

    public AbstractGroupPanel(
            AbstractNodeDetailsPanel<GROUP_T, CONCEPT_T> groupDetailsPanel, 
            AbstractGroupHierarchyPanel<CONCEPT_T, GROUP_T> groupHierarchyPanel, 
            BLUAbNConfiguration configuration) {
        
        super(groupDetailsPanel, configuration);
        
        this.groupHierarchyPanel = groupHierarchyPanel;

        addGroupDetailsTab(groupHierarchyPanel, String.format("%s Hierarchy", configuration.getGroupTypeName(false)));
    }
    
    @Override
    protected String getNodeTitle(GROUP_T node) {
        return configuration.getGroupName(node);
    }

    @Override
    protected String getNodeType() {
        return configuration.getGroupTypeName(false);
    }
}
