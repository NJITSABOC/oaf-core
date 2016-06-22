
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.factory.NodeTypeNameFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.label.DetailsPanelLabel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public class NodeDashboardPanel extends BaseNodeInformationPanel {
    
    private final DetailsPanelLabel groupNameLabel;
    
    private final ArrayList<BaseNodeInformationPanel> groupDetailsPanels = new ArrayList<>();
    
    private final JTabbedPane tabbedPane;
    
    private final NodeDetailsPanel nodeDetailsPanel;
    
    private final BLUConfiguration configuration;

    public NodeDashboardPanel(
            NodeDetailsPanel nodeDetailsPanel, 
            BLUConfiguration configuration, 
            NodeTypeNameFactory nodeTypeName) {
        
        this.configuration = configuration;
        this.nodeDetailsPanel = nodeDetailsPanel;
        
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        groupNameLabel = new DetailsPanelLabel(" ");
        
        tabbedPane = new JTabbedPane();
        
        addGroupDetailsTab(this.nodeDetailsPanel, String.format("%s Details", nodeTypeName.getNodeTypeName(false)));

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
        
        groupDetailsPanels.forEach((BaseNodeInformationPanel gdp) -> {
            gdp.clearContents();
        });
    }

    
    public final int addGroupDetailsTab(BaseNodeInformationPanel panel, String tabName) {
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
