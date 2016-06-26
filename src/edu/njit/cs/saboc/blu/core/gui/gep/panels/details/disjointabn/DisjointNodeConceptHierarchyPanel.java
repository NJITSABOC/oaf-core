package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.NodeConceptHierarchicalViewPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.BaseNodeInformationPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Chris O
 */
public abstract class DisjointNodeConceptHierarchyPanel extends BaseNodeInformationPanel {

    private final NodeConceptHierarchicalViewPanel conceptHierarchyPanel;
    
    private final JScrollPane scrollPane;
    
    public DisjointNodeConceptHierarchyPanel(
            NodeConceptHierarchicalViewPanel conceptHierarchyPanel, 
            DisjointAbNConfiguration configuration) {
        
        this.setLayout(new BorderLayout());
        
        this.conceptHierarchyPanel = conceptHierarchyPanel;
        
        scrollPane = new JScrollPane(conceptHierarchyPanel);
        
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void setContents(Node disjointNode) {
        conceptHierarchyPanel.setNode((SinglyRootedNode)disjointNode);
    }

    @Override
    public void clearContents() {
        conceptHierarchyPanel.setNode(null);
    }
}
