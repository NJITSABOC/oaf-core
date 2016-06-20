
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.label.DetailsPanelLabel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public class NodeInformationPanel extends AbNNodeInformationPanel {
    
    private final DetailsPanelLabel groupNameLabel;
    
    private final ArrayList<AbNNodeInformationPanel> groupDetailsPanels = new ArrayList<>();
    
    private final JTabbedPane tabbedPane;
    
    private final AbstractNodeDetailsPanel nodeDetailsPanel;
    
    private final BLUConfiguration configuration;

    protected NodeInformationPanel(AbstractNodeDetailsPanel nodeDetailsPanel, BLUConfiguration configuration) {
        this.configuration = configuration;
        this.nodeDetailsPanel = nodeDetailsPanel;
        
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        groupNameLabel = new DetailsPanelLabel(" ");
        
        tabbedPane = new JTabbedPane();
        
        addGroupDetailsTab(this.nodeDetailsPanel, String.format("%s Details", getNodeType()));

        this.add(groupNameLabel, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    public BLUConfiguration getConfiguration() {
        return configuration;
    }
    
    public void setContents(Node node) {
        groupNameLabel.setText(node.getName());
        
        groupDetailsPanels.forEach((gdp) -> {
            gdp.setContents(node);
        });
    }
    
    public void clearContents() {
        groupNameLabel.setText("");
        
        groupDetailsPanels.forEach((AbNNodeInformationPanel gdp) -> {
            gdp.clearContents();
        });
    }

    
    public final int addGroupDetailsTab(AbNNodeInformationPanel panel, String tabName) {
        tabbedPane.addTab(tabName, panel);
        groupDetailsPanels.add(panel);
        
        return tabbedPane.getTabCount() - 1;
    }
    
    public final void enableGroupDetailsTabAt(int index, boolean enabled) {
        tabbedPane.setEnabledAt(index, enabled);
        
        if(!enabled && tabbedPane.getSelectedIndex() == index) {
            tabbedPane.setSelectedIndex(0);
        }
    }
}
