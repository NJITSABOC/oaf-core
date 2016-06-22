package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptGroupHierarchicalViewPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.BaseNodeInformationPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Chris O
 */
public abstract class PAreaConceptHierarchyPanel<CONCEPT_T, PAREA_T extends PArea, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T>> extends BaseNodeInformationPanel<PAREA_T> {

    protected ConceptGroupHierarchicalViewPanel<CONCEPT_T> conceptHierarchyPanel;
    
    private JScrollPane scrollPane;
    
    public PAreaConceptHierarchyPanel(ConceptGroupHierarchicalViewPanel<CONCEPT_T> conceptHierarchyPanel, 
            BLUGenericPAreaTaxonomyConfiguration configuration) {
        
        this.setLayout(new BorderLayout());
        
        this.conceptHierarchyPanel = conceptHierarchyPanel;
        
        scrollPane = new JScrollPane(conceptHierarchyPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.add(scrollPane, BorderLayout.CENTER);
    }    

    @Override
    public void setContents(PAREA_T parea) {
        conceptHierarchyPanel.setGroup(parea);
    }

    @Override
    public void clearContents() {
        conceptHierarchyPanel.setGroup(null);
    }
}
