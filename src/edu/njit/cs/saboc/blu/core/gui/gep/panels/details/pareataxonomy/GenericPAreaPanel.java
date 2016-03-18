package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class GenericPAreaPanel<CONCEPT_T, PAREA_T extends GenericPArea, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T>,
        CONFIG_T extends BLUGenericPAreaTaxonomyConfiguration> extends AbstractGroupPanel<PAREA_T, CONCEPT_T, CONFIG_T> {
    
    protected final PAreaConceptHierarchyPanel<CONCEPT_T, PAREA_T, HIERARCHY_T> conceptHierarchyPanel;
    
    public GenericPAreaPanel(
            AbstractNodeDetailsPanel<PAREA_T, CONCEPT_T> pareaDetailsPanel, 
            AbstractGroupHierarchyPanel<CONCEPT_T, PAREA_T, CONFIG_T> pareaHierarchyPanel,
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
