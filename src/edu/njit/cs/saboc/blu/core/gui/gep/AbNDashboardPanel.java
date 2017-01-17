package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.SimpleAbNDetailsPanel;
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
    private Optional<SimpleAbNDetailsPanel> abnDetailsPanel = Optional.empty();
    
    private Optional<NodeDashboardPanel> groupDetailsPanel = Optional.empty();
    private Optional<NodeDashboardPanel> containerDetailsPanel = Optional.empty();
    
    public AbNDashboardPanel() {
        super(new BorderLayout());
        
        loadingPanel = new LoadingPanel();
    }
    
    public void initialize(AbNConfiguration configuration) {
        this.abnDetailsPanel = Optional.of(configuration.getUIConfiguration().createAbNDetailsPanel());

        if (abnDetailsPanel.isPresent()) {
            this.setDetailsPanelContents(abnDetailsPanel.get());
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
            setDetailsPanelContents(loadingPanel);
            
            groupDetailsPanel.get().clearContents();
            
            Thread loadThread = new Thread( () -> {
                groupDetailsPanel.get().setContents(node);
                
                SwingUtilities.invokeLater( () -> {
                    setDetailsPanelContents(groupDetailsPanel.get());
                });
            });
            
            loadThread.start();
        }
    }
    
    public void displayDetailsForPartitionedNode(PartitionedNode container) {
        if (groupDetailsPanel.isPresent()) {
            groupDetailsPanel.get().clearContents();
        }

        if (containerDetailsPanel.isPresent()) {
            setDetailsPanelContents(loadingPanel);
            
            containerDetailsPanel.get().clearContents();
            
            Thread loadThread = new Thread( () -> {
                containerDetailsPanel.get().setContents(container);
                
                SwingUtilities.invokeLater( () -> {
                    setDetailsPanelContents(containerDetailsPanel.get());
                });
            });
            
            loadThread.start();
        }
    }

    private void setDetailsPanelContents(JPanel panel) {
        this.removeAll();
        
        this.add(panel, BorderLayout.CENTER);
        
        this.revalidate();
        this.repaint();
    }
    
    public void reset() {
        if (groupDetailsPanel.isPresent()) {
            groupDetailsPanel.get().clearContents();
        }

        if (containerDetailsPanel.isPresent()) {
            containerDetailsPanel.get().clearContents();
        }

        if(abnDetailsPanel.isPresent()) {
            setDetailsPanelContents(abnDetailsPanel.get());
        }
    }
    
    public void clear() {
        abnDetailsPanel = Optional.empty();
        containerDetailsPanel = Optional.empty();
        groupDetailsPanel = Optional.empty();
        
        this.setDetailsPanelContents(loadingPanel);
    }
}
