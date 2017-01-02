package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel.AbNEntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


/**
 *
 * @author Chris
 */
public class AbNExplorationPanel extends JPanel {
    
    private AbNConfiguration configuration;
    
    private final JSplitPane splitPane;
    
    private final AbNDashboardPanel dashboardPanel = new AbNDashboardPanel();
    private final AbNDisplayPanel displayPanel = new AbNDisplayPanel();

    public AbNExplorationPanel() {
        super(new BorderLayout());

        splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        splitPane.setLeftComponent(dashboardPanel);
        splitPane.setRightComponent(displayPanel);
        
        this.addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                splitPane.setDividerLocation(500);
            }
        });

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
                
                PartitionedAbstractionNetwork partitionedAbN = (PartitionedAbstractionNetwork)configuration.getAbstractionNetwork();

                PartitionedNode node = partitionedAbN.getPartitionNodeFor((SinglyRootedNode)entry.getNode().getInternalNodes().iterator().next());

                dashboardPanel.displayDetailsForPartitionedNode(node);
            }

            @Override
            public void noEntriesSelected() {
                dashboardPanel.clearContents();
            }
        });

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    public AbNDashboardPanel getDashboardPanel() {
        return dashboardPanel;
    }
    
    public AbNDisplayPanel getDisplayPanel() {
        return displayPanel;
    }
    
    public void showLoading() {
        displayPanel.doLoading();
    }
    
     public void initialize(
            BluGraph graph, 
            AbNConfiguration config, 
            AbNPainter painter) {
         
         initialize(graph, config, painter, new BaseAbNExplorationPanelInitializer());
     }
    
    public void initialize(
            BluGraph graph, 
            AbNConfiguration config, 
            AbNPainter painter,
            AbNExplorationPanelGUIInitializer initializer) {
        
        this.configuration = config;
                
        dashboardPanel.initialize(config);
        displayPanel.initialize(graph, painter, initializer.getInitialDisplayAction());

        // Add display-specific widgets and wizbangs
        initializer.initializeAbNDisplayPanel(displayPanel);
        initializer.initializeAbNDDashboardPanel(dashboardPanel);
        
                
        displayPanel.resetUpdateables();
        
        config.getUIConfiguration().setDisplayPanel(displayPanel);
    }
}
