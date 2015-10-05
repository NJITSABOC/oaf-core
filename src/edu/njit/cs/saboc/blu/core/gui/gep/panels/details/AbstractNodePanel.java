
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.label.DetailsPanelLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public abstract class AbstractNodePanel<NODE_T, CONCEPT_T, CONFIG_T extends BLUConfiguration> extends AbNNodeInformationPanel<NODE_T> {
    
    private final DetailsPanelLabel groupNameLabel;
    
    private final ArrayList<AbNNodeInformationPanel<NODE_T>> groupDetailsPanels = new ArrayList<>();
    
    private final JTabbedPane tabbedPane;
    
    private final AbstractNodeDetailsPanel<NODE_T, CONCEPT_T> nodeDetailsPanel;
    
    private final CONFIG_T configuration;

    protected AbstractNodePanel(AbstractNodeDetailsPanel<NODE_T, CONCEPT_T> nodeDetailsPanel, CONFIG_T configuration) {
        this.configuration = configuration;
        this.nodeDetailsPanel = nodeDetailsPanel;
        
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        groupNameLabel = new DetailsPanelLabel(" ");
                
        groupNameLabel.setFont(groupNameLabel.getFont().deriveFont(Font.BOLD, 20));
        groupNameLabel.setPreferredSize(new Dimension(100, 40));
        
        tabbedPane = new JTabbedPane();
        
        addGroupDetailsTab(this.nodeDetailsPanel, String.format("%s Details", getNodeType()));

        this.add(groupNameLabel, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);
    }
    
    public CONFIG_T getConfiguration() {
        return configuration;
    }
    
    public void setContents(NODE_T node) {
        groupNameLabel.setText(getNodeTitle(node));
        
        groupDetailsPanels.forEach((AbNNodeInformationPanel<NODE_T> gdp) -> {
            gdp.setContents(node);
        });
    }
    
    public void clearContents() {
        groupNameLabel.setText("");
        
        groupDetailsPanels.forEach((AbNNodeInformationPanel<NODE_T> gdp) -> {
            gdp.clearContents();
        });
    }

    
    public final int addGroupDetailsTab(AbNNodeInformationPanel<NODE_T> panel, String tabName) {
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
    
    protected abstract String getNodeTitle(NODE_T node);
    protected abstract String getNodeType();
}
