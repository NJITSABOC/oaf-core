package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.loading;

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
public class LoadingPanel extends JPanel {
    
    private final DetailsPanelLabel loadingLabel;
    private final JEditorPane loadingDetailsPane;
    
    public LoadingPanel() {
        
        loadingLabel = new DetailsPanelLabel(" ");
        
        loadingLabel.setText("Loading.... Please wait.");
        loadingLabel.setFont(loadingLabel.getFont().deriveFont(Font.BOLD, 20));
        loadingLabel.setPreferredSize(new Dimension(100, 40));
        
        loadingDetailsPane = new JEditorPane();
        loadingDetailsPane.setContentType("text/html");
        loadingDetailsPane.setEnabled(true);
        loadingDetailsPane.setEditable(false);
        loadingDetailsPane.setFont(loadingDetailsPane.getFont().deriveFont(Font.BOLD, 14));
        loadingDetailsPane.setText("One moment please. Information about the selected abstraction network component is currently "
                + "being retreived. This screen may appear for a moment if a large amount of information is being processed.");
               
        this.setLayout(new BorderLayout());
        
        this.add(loadingLabel, BorderLayout.NORTH);
        this.add(new JScrollPane(loadingDetailsPane), BorderLayout.CENTER);
    }
}
