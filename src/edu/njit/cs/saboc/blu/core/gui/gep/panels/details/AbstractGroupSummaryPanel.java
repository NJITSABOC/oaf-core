package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

/**
 *
 * @author Den
 */
public abstract class AbstractGroupSummaryPanel<T extends GenericConceptGroup> extends GroupInformationPanel<T> {

    private final JEditorPane groupDetailsPane;
    
    public AbstractGroupSummaryPanel() {
        
        this.setLayout(new BorderLayout());
        
        groupDetailsPane = new JEditorPane();
        groupDetailsPane.setContentType("text/html");
        groupDetailsPane.setEnabled(true);
        groupDetailsPane.setEditable(false);
        
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(groupDetailsPane, BorderLayout.CENTER);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Details"));
        
        this.add(detailsPanel, BorderLayout.NORTH);

    }
    
    public void setContents(T group) {
        groupDetailsPane.setText(group.getRoot().getName());
    }
    
    public void clearContents() {
        
    }
}
