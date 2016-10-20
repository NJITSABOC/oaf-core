package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.loading.LoadingPanel;
import java.awt.BorderLayout;
import java.util.Optional;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class AbNDashboardPanel extends JPanel {
    
    private final LoadingPanel loadingPanel;
    private AbstractAbNDetailsPanel abnDetailsPanel;
    
    private Optional<NodeDashboardPanel> groupDetailsPanel;
    private Optional<NodeDashboardPanel> containerDetailsPanel;
    
    public AbNDashboardPanel() {
        super(new BorderLayout());
        
        loadingPanel = new LoadingPanel();
    }
    
    public void setAbNDetailsComponents(AbNConfiguration configuration) {
        this.abnDetailsPanel = configuration.getUIConfiguration().createAbNDetailsPanel();

        if (abnDetailsPanel != null) {
            this.setDetailsPanelContents(abnDetailsPanel);
        }

        if (configuration.getUIConfiguration().hasGroupDetailsPanel()) {
            groupDetailsPanel = Optional.of(configuration.getUIConfiguration().createGroupDetailsPanel());
        } else {
            this.groupDetailsPanel = Optional.empty();
        }

        if (configuration.getUIConfiguration() instanceof PartitionedAbNUIConfiguration) {
            PartitionedAbNUIConfiguration config = (PartitionedAbNUIConfiguration) configuration.getUIConfiguration();

            if (config.hasContainerDetailsPanel()) {
                containerDetailsPanel = Optional.of(config.createContainerDetailsPanel());
            } else {
                this.containerDetailsPanel = Optional.empty();
            }
        } else {
            this.containerDetailsPanel = Optional.empty();
        }
    }
    
    public void displayDetailsForNode(SinglyRootedNode node) {
        if (containerDetailsPanel.isPresent()) {
            containerDetailsPanel.get().clearContents();
        }
        
        if (groupDetailsPanel.isPresent()) {
            groupDetailsPanel.get().clearContents();
            groupDetailsPanel.get().setContents(node);
            setDetailsPanelContents(groupDetailsPanel.get());
        }
    }
    
    public void displayDetailsForPartitionedNode(PartitionedNode container) {
        if (groupDetailsPanel.isPresent()) {
            groupDetailsPanel.get().clearContents();
        }

        if (containerDetailsPanel.isPresent()) {
            containerDetailsPanel.get().clearContents();
            containerDetailsPanel.get().setContents(container);

            setDetailsPanelContents(containerDetailsPanel.get());
        }
    }

    private void setDetailsPanelContents(JPanel panel) {
        this.removeAll();
        this.add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    
    public void clearContents() {
        if (groupDetailsPanel.isPresent()) {
            groupDetailsPanel.get().clearContents();
        }

        if (containerDetailsPanel.isPresent()) {
            containerDetailsPanel.get().clearContents();
        }

        setDetailsPanelContents(abnDetailsPanel);
    }
}
