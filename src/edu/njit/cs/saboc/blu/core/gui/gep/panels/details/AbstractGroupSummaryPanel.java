package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

/**
 *
 * @author Den
 */
public abstract class AbstractGroupSummaryPanel<T extends GenericConceptGroup> extends GroupInformationPanel<T> {

    private final JEditorPane groupDetailsPane;
    
    public AbstractGroupSummaryPanel() {
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        groupDetailsPane = new JEditorPane();
        groupDetailsPane.setContentType("text/html");
        groupDetailsPane.setEnabled(true);
        groupDetailsPane.setEditable(false);
        groupDetailsPane.setFont(groupDetailsPane.getFont().deriveFont(Font.BOLD, 14));

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(groupDetailsPane, BorderLayout.CENTER);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Details"));
        detailsPanel.setMinimumSize(new Dimension(-1, 100));
        detailsPanel.setPreferredSize(new Dimension(-1, 100));
        
        this.add(detailsPanel);
    }
    
    protected abstract String createDescriptionStr(T group);
    
    public void setContents(T group) {
        groupDetailsPane.setText(createDescriptionStr(group));
    }
    
    public void clearContents() {
        groupDetailsPane.setText("");
    }
}
