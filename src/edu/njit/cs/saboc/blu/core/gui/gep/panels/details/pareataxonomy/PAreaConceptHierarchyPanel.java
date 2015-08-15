package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptGroupHierarchicalViewPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNNodeInformationPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Chris O
 */
public abstract class PAreaConceptHierarchyPanel<CONCEPT_T, PAREA_T extends GenericPArea, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>> extends AbNNodeInformationPanel<PAREA_T> {

    protected ConceptGroupHierarchicalViewPanel<CONCEPT_T, HIERARCHY_T> conceptHierarchyPanel;
    
    private JScrollPane scrollPane;
    
    public PAreaConceptHierarchyPanel() {
        this.setLayout(new BorderLayout());
    }    

    @Override
    public void setContents(PAREA_T parea) {
        conceptHierarchyPanel.setGroup(parea);
    }

    @Override
    public void initUI() {
        conceptHierarchyPanel = createConceptHierchyPanel();
        
        scrollPane = new JScrollPane(conceptHierarchyPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void clearContents() {
        conceptHierarchyPanel.setGroup(null);
    }
        
    protected abstract ConceptGroupHierarchicalViewPanel<CONCEPT_T, HIERARCHY_T> createConceptHierchyPanel();
}
