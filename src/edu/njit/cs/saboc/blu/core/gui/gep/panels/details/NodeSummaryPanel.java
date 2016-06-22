package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeSummaryTextFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Den
 */
public class NodeSummaryPanel extends BaseNodeInformationPanel {

    private final JEditorPane nodeDetailsPane;
    
    private final NodeSummaryTextFactory textFactory;
    
    public NodeSummaryPanel(NodeSummaryTextFactory textFactory) {
        
        this.textFactory = textFactory;
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        nodeDetailsPane = new JEditorPane();
        nodeDetailsPane.setContentType("text/html");
        nodeDetailsPane.setEnabled(true);
        nodeDetailsPane.setEditable(false);
        nodeDetailsPane.setFont(nodeDetailsPane.getFont().deriveFont(Font.BOLD, 14));

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(new JScrollPane(nodeDetailsPane), BorderLayout.CENTER);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Details"));
        detailsPanel.setMinimumSize(new Dimension(-1, 100));
        detailsPanel.setPreferredSize(new Dimension(-1, 100));
        
        this.add(detailsPanel);
    }
    
    public void setContents(Node node) {
        nodeDetailsPane.setText(textFactory.createNodeSummaryText(node));
        
        nodeDetailsPane.setSelectionStart(0);
        nodeDetailsPane.setSelectionEnd(0);
    }
    
    public void clearContents() {
        nodeDetailsPane.setText("");
    }
}
