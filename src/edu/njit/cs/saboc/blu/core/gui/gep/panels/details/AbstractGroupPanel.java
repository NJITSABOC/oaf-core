package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;

/**
 *
 * @author Chris O
 */
public class AbstractGroupPanel<
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T,
        CONFIG_T extends BLUConfiguration> extends AbstractNodePanel<GROUP_T, CONCEPT_T, CONFIG_T> {

    private final AbstractGroupHierarchyPanel<CONCEPT_T, GROUP_T, CONFIG_T> groupHierarchyPanel;

    public AbstractGroupPanel(
            AbstractNodeDetailsPanel<GROUP_T, CONCEPT_T> groupDetailsPanel, 
            AbstractGroupHierarchyPanel<CONCEPT_T, GROUP_T, CONFIG_T> groupHierarchyPanel, 
            CONFIG_T configuration) {
        
        super(groupDetailsPanel, configuration);
        
        this.groupHierarchyPanel = groupHierarchyPanel;

        addGroupDetailsTab(groupHierarchyPanel, String.format("%s Hierarchy", configuration.getTextConfiguration().getGroupTypeName(false)));
    }
    
    @Override
    protected String getNodeTitle(GROUP_T node) {
        return getConfiguration().getTextConfiguration().getGroupName(node);
    }

    @Override
    protected String getNodeType() {
        return getConfiguration().getTextConfiguration().getGroupTypeName(false);
    }
}
