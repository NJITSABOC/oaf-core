package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

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
public abstract class AbstractNodeSummaryPanel<NODE_T> extends AbNNodeInformationPanel<NODE_T> {

    private final JEditorPane nodeDetailsPane;
    
    public AbstractNodeSummaryPanel() {
        
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
    
    protected abstract String createDescriptionStr(NODE_T group);
    
    public void setContents(NODE_T group) {
        nodeDetailsPane.setText(createDescriptionStr(group));
        
        nodeDetailsPane.setSelectionStart(0);
        nodeDetailsPane.setSelectionEnd(0);
        
    }
    
    public void clearContents() {
        nodeDetailsPane.setText("");
    }
}
