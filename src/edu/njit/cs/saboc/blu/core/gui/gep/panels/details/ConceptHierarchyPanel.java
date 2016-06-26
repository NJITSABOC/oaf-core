package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.NodeConceptHierarchicalViewPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Den
 */
public class ConceptHierarchyPanel extends BaseNodeInformationPanel {

    private final NodeConceptHierarchicalViewPanel conceptHierarchyPanel;
    
    private final JScrollPane scrollPane;
    
    public ConceptHierarchyPanel(AbNConfiguration configuration) {
        
        this.setLayout(new BorderLayout());
        
        this.conceptHierarchyPanel = new NodeConceptHierarchicalViewPanel(configuration);
        
        scrollPane = new JScrollPane(conceptHierarchyPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.add(scrollPane, BorderLayout.CENTER);
    }    

    @Override
    public void setContents(Node node) {
        conceptHierarchyPanel.setNode((SinglyRootedNode)node);
    }

    @Override
    public void clearContents() {
        conceptHierarchyPanel.setNode(null);
    }
}