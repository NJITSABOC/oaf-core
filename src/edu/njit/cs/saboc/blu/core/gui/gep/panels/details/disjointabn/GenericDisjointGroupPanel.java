package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class GenericDisjointGroupPanel<CONCEPT_T, 
        DISJOINTGROUP_T extends DisjointGenericConceptGroup<GROUP_T, CONCEPT_T, HIERARCHY_T, DISJOINTGROUP_T>, 
        GROUP_T extends GenericConceptGroup,
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>> extends AbstractGroupPanel<DISJOINTGROUP_T, CONCEPT_T> {
    
    protected final GenericDisjointGroupConceptHierarchyPanel<CONCEPT_T, DISJOINTGROUP_T, HIERARCHY_T> conceptHierarchyPanel;
    
    public GenericDisjointGroupPanel(
            AbstractNodeDetailsPanel<DISJOINTGROUP_T, CONCEPT_T> disjointGroupDetailsPanel, 
            AbstractGroupHierarchyPanel<CONCEPT_T, DISJOINTGROUP_T> disjointGroupHierarchyPanel,
            GenericDisjointGroupConceptHierarchyPanel<CONCEPT_T, DISJOINTGROUP_T, HIERARCHY_T> conceptHierarchyPanel,
            BLUDisjointAbNConfiguration configuration) {
        
        super(disjointGroupDetailsPanel, disjointGroupHierarchyPanel, configuration);
        
        this.conceptHierarchyPanel = conceptHierarchyPanel;
          
        this.addGroupDetailsTab(conceptHierarchyPanel, String.format("%s Hierarchy in %s", 
                configuration.getConceptTypeName(false),
                configuration.getDisjointGroupTypeName(false)));
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
        BLUDisjointAbNConfiguration currentConfig = (BLUDisjointAbNConfiguration)configuration;
        
        return currentConfig.getDisjointGroupName(node);
    }

    @Override
    protected String getNodeType() {
        BLUDisjointAbNConfiguration currentConfig = (BLUDisjointAbNConfiguration)configuration;
        
        return currentConfig.getDisjointGroupTypeName(false);
    }
}
