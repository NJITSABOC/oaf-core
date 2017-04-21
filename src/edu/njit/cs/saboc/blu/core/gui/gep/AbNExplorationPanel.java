package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel.AbNEntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import java.awt.BorderLayout;
import javax.swing.JPanel;


/**
 *
 * @author Chris
 */
public class AbNExplorationPanel extends JPanel {
    
    private AbNConfiguration configuration;

    private final AbNDashboardPanel dashboardPanel = new AbNDashboardPanel();
    private final AbNDisplayPanel displayPanel = new AbNDisplayPanel();

    private final FloatingAbNDashboardFrame dashboardFrame;
    
    public AbNExplorationPanel() {
        super(new BorderLayout());

        dashboardFrame = new FloatingAbNDashboardFrame(
                this,
                dashboardPanel);

        displayPanel.addAbNSelectionListener(new AbNEntitySelectionListener() {

            @Override
            public void nodeEntrySelected(SinglyRootedNodeEntry nodeEntry) {
                dashboardPanel.displayDetailsForNode(nodeEntry.getNode());
            }

            @Override
            public void partitionEntrySelected(GenericPartitionEntry entry) {
                if(!(configuration.getAbstractionNetwork() instanceof PartitionedAbstractionNetwork)) {
                    return;
                }
                
                PartitionedNode parentNode = entry.getParentContainer().getNode();

                dashboardPanel.displayDetailsForPartitionedNode(parentNode);
            }

            @Override
            public void noEntriesSelected() {
                dashboardPanel.reset();
            }
        });
        
        displayPanel.add(dashboardFrame);
        
        dashboardFrame.setLocation(10, 160);

        this.add(displayPanel, BorderLayout.CENTER);
    }
    
    public AbNDashboardPanel getDashboardPanel() {
        return dashboardPanel;
    }
    
    public AbNDisplayPanel getDisplayPanel() {
        return displayPanel;
    }
    
    public void showLoading() {
        dashboardFrame.setVisible(false);
        displayPanel.doLoading();
    }
    
     public void initialize(
            AbstractionNetworkGraph graph, 
            AbNConfiguration config, 
            AbNPainter painter) {
         
         initialize(graph, config, painter, new BaseAbNExplorationPanelInitializer());
     }
    
    public void initialize(
            AbstractionNetworkGraph graph, 
            AbNConfiguration config, 
            AbNPainter painter,
            AbNExplorationPanelGUIInitializer initializer) {
        
        this.configuration = config;
        
        dashboardPanel.clear();
        displayPanel.clearWidgets();
                
        dashboardPanel.initialize(config);
        displayPanel.initialize(graph, painter, initializer.getInitialDisplayAction());

        // Add display-specific widgets and wizbangs
        initializer.initializeAbNDisplayPanel(displayPanel);
        initializer.initializeAbNDDashboardPanel(dashboardPanel);
        
        displayPanel.resetUpdateables();
        displayPanel.updateWidgetLocations();
        
        config.getUIConfiguration().setDisplayPanel(displayPanel);
        
        dashboardFrame.setVisible(true);
    }
}
