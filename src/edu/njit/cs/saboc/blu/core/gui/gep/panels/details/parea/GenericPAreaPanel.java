package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.parea;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupPanel;

/**
 *
 * @author Chris O
 */
public abstract class GenericPAreaPanel<CONCEPT_T, PAREA_T extends GenericPArea, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>> extends AbstractGroupPanel<PAREA_T, CONCEPT_T> {
    
    protected PAreaConceptHierarchyPanel<CONCEPT_T, PAREA_T, HIERARCHY_T> conceptHierarchyPanel;
    
    protected final String conceptType;
    
    public GenericPAreaPanel(String conceptType) {
        this.conceptType = conceptType;
    }
    
    public void initUI() {
        super.initUI();
        
        conceptHierarchyPanel = createPAreaConceptHierarchyPanel();
        
        conceptHierarchyPanel.initUI();
        
        this.addGroupDetailsTab(conceptHierarchyPanel, String.format("%s Hierarchy in Partial-area", conceptType));
    }
    
    public void setContents(PAREA_T parea) {
        super.setContents(parea);
        
        conceptHierarchyPanel.setContents(parea);
    }
    
    public String getGroupType() {
        return "Partial-area";
    }
    
    protected abstract PAreaConceptHierarchyPanel<CONCEPT_T, PAREA_T, HIERARCHY_T> createPAreaConceptHierarchyPanel();

}
