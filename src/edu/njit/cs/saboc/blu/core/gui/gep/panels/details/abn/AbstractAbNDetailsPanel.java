package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public class AbstractAbNDetailsPanel<ABN_T extends AbstractionNetwork> extends JPanel {
    
    private final GenericAbNSummaryPanel<ABN_T> abnSummaryPanel;
    
    private final JTabbedPane abnDetailsPane;
    
    public AbstractAbNDetailsPanel(AbNConfiguration config) {
        
        this.abnSummaryPanel = new GenericAbNSummaryPanel<>(config);
        
        this.abnDetailsPane = new JTabbedPane();
        
        this.setLayout(new BorderLayout());
        
        abnDetailsPane.addTab("Summary", abnSummaryPanel);
        
        this.add(abnDetailsPane, BorderLayout.CENTER);
    }
    
    public void addDetailsTab(String tabName, JPanel panel) {
        abnDetailsPane.addTab(tabName, panel);
    }
}
