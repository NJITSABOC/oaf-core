package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.label.DetailsPanelLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Chris O
 */
public class GenericAbNSummaryPanel<ABN_T extends AbstractionNetwork> extends JPanel {
    
    private final DetailsPanelLabel abnNameLabel;
    
    private final JEditorPane abnDetailsPane;
    
    public GenericAbNSummaryPanel(BLUConfiguration config) {
        
        abnNameLabel = new DetailsPanelLabel(" ");
        
        abnNameLabel.setText(config.getTextConfiguration().getAbNName());
        abnNameLabel.setFont(abnNameLabel.getFont().deriveFont(Font.BOLD, 20));
        abnNameLabel.setPreferredSize(new Dimension(100, 40));
        
        abnDetailsPane = new JEditorPane();
        abnDetailsPane.setContentType("text/html");
        abnDetailsPane.setEnabled(true);
        abnDetailsPane.setEditable(false);
        abnDetailsPane.setFont(abnDetailsPane.getFont().deriveFont(Font.BOLD, 14));
        abnDetailsPane.setText(config.getTextConfiguration().getAbNSummary());
               
        this.setLayout(new BorderLayout());
        
        this.add(abnNameLabel, BorderLayout.NORTH);
        this.add(new JScrollPane(abnDetailsPane), BorderLayout.CENTER);
    }
}