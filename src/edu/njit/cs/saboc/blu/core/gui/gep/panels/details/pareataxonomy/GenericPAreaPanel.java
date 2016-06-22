package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.SinglyRootedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class GenericPAreaPanel<CONCEPT_T, PAREA_T extends PArea, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T>,
        CONFIG_T extends BLUGenericPAreaTaxonomyConfiguration> extends SinglyRootedNodePanel<PAREA_T, CONCEPT_T, CONFIG_T> {
    
    protected final PAreaConceptHierarchyPanel<CONCEPT_T, PAREA_T, HIERARCHY_T> conceptHierarchyPanel;
    
    public GenericPAreaPanel(
            NodeDetailsPanel<PAREA_T, CONCEPT_T> pareaDetailsPanel, 
            NodeHierarchyPanel<CONCEPT_T, PAREA_T, CONFIG_T> pareaHierarchyPanel,
            PAreaConceptHierarchyPanel<CONCEPT_T, PAREA_T, HIERARCHY_T> conceptHierarchyPanel,
            CONFIG_T configuration) {
        
        super(pareaDetailsPanel, pareaHierarchyPanel, configuration);
        
        this.conceptHierarchyPanel = conceptHierarchyPanel;
          
        this.addGroupDetailsTab(conceptHierarchyPanel, String.format("%s Hierarchy in Partial-area", 
                configuration.getTextConfiguration().getConceptTypeName(false)));
    }
        
    public void setContents(PAREA_T parea) {
        super.setContents(parea);
        
        conceptHierarchyPanel.setContents(parea);
    }
    
    public void clearContents() {
        conceptHierarchyPanel.clearContents();
    }
}
