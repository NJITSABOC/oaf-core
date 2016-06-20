package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class GenericDisjointGroupPanel<CONCEPT_T, 
        DISJOINTGROUP_T extends DisjointNode<GROUP_T, CONCEPT_T, HIERARCHY_T, DISJOINTGROUP_T>, 
        GROUP_T extends GenericConceptGroup,
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T>,
        CONFIG_T extends BLUDisjointConfiguration> extends AbstractGroupPanel<DISJOINTGROUP_T, CONCEPT_T, CONFIG_T> {
    
    protected final GenericDisjointGroupConceptHierarchyPanel<CONCEPT_T, DISJOINTGROUP_T, HIERARCHY_T> conceptHierarchyPanel;
    
    public GenericDisjointGroupPanel(
            AbstractNodeDetailsPanel<DISJOINTGROUP_T, CONCEPT_T> disjointGroupDetailsPanel, 
            AbstractGroupHierarchyPanel<CONCEPT_T, DISJOINTGROUP_T, CONFIG_T> disjointGroupHierarchyPanel,
            GenericDisjointGroupConceptHierarchyPanel<CONCEPT_T, DISJOINTGROUP_T, HIERARCHY_T> conceptHierarchyPanel,
            CONFIG_T configuration) {
        
        super(disjointGroupDetailsPanel, disjointGroupHierarchyPanel, configuration);
        
        this.conceptHierarchyPanel = conceptHierarchyPanel;
          
        this.addGroupDetailsTab(conceptHierarchyPanel, String.format("%s Hierarchy in %s", 
                configuration.getTextConfiguration().getConceptTypeName(false),
                configuration.getTextConfiguration().getGroupTypeName(false)));
    }
        
    public void setContents(DISJOINTGROUP_T parea) {
        super.setContents(parea);
        
        conceptHierarchyPanel.setContents(parea);
    }
    
    public void clearContents() {
        conceptHierarchyPanel.clearContents();
    }
    
    @Override
    protected String getNodeTitle(DISJOINTGROUP_T node) {
        return getConfiguration().getTextConfiguration().getGroupName(node);
    }

    @Override
    protected String getNodeType() {       
        return getConfiguration().getTextConfiguration().getGroupTypeName(false);
    }
}
