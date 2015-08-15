package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;

/**
 *
 * @author Chris O
 */
public abstract class AbstractGroupPanel<GROUP_T extends GenericConceptGroup, CONCEPT_T> extends AbstractNodePanel<GROUP_T, CONCEPT_T> {

    private AbstractGroupHierarchyPanel<CONCEPT_T, GROUP_T> groupHierarchyPanel;

    public AbstractGroupPanel() {
        
    }

    public void initUI() {
        super.initUI();
        
        this.groupHierarchyPanel = createGroupHierarchyPanel();
        
        groupHierarchyPanel.initUI();
        
        addGroupDetailsTab(groupHierarchyPanel, String.format("%s Hierarchy", getNodeType()));
    }

    protected abstract AbstractGroupHierarchyPanel<CONCEPT_T, GROUP_T> createGroupHierarchyPanel();
}
