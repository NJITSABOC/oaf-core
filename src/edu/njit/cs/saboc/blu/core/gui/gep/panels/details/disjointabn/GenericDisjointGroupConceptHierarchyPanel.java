package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptGroupHierarchicalViewPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.BaseNodeInformationPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Chris O
 */
public abstract class GenericDisjointGroupConceptHierarchyPanel<
        CONCEPT_T, 
        DISJOINTGROUP_T extends DisjointNode, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T>> extends BaseNodeInformationPanel<DISJOINTGROUP_T> {

    protected ConceptGroupHierarchicalViewPanel<CONCEPT_T> conceptHierarchyPanel;
    
    private JScrollPane scrollPane;
    
    public GenericDisjointGroupConceptHierarchyPanel(
            ConceptGroupHierarchicalViewPanel<CONCEPT_T> conceptHierarchyPanel, 
            BLUDisjointConfiguration configuration) {
        
        this.setLayout(new BorderLayout());
        
        this.conceptHierarchyPanel = conceptHierarchyPanel;
        
        scrollPane = new JScrollPane(conceptHierarchyPanel);
        
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void setContents(DISJOINTGROUP_T disjointGroup) {
        conceptHierarchyPanel.setGroup(disjointGroup);
    }

    @Override
    public void clearContents() {
        conceptHierarchyPanel.setGroup(null);
    }
}
