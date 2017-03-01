package edu.njit.cs.saboc.blu.core.gui.graphframe;

import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.tan.BandTANGraph;
import edu.njit.cs.saboc.blu.core.graph.tan.ClusterTANGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.AggregateableAbNExplorationPanelInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.exportabn.ExportPartitionedAbNButton;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateSinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.PartitionedAbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.tan.AggregateTANPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.tan.TANPainter;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PartitionedAbNSelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.PartitionedAbNSearchButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ClusterInternalGraphFrame extends GenericInternalGraphFrame<ClusterTribalAbstractionNetwork> {
    
    public interface ClusterConfigurationConfiguration {
        public TANConfiguration createConfigurationFor(ClusterTribalAbstractionNetwork tan, AbNDisplayManager displayManager);
    }
    
    public interface DisplayReportsAction {
        public void displayReports(TANConfiguration config);
    }
    
    private final PartitionedAbNSearchButton searchButton;
    
    private final AbNDisplayManager displayListener;
    
    private final JButton openReportsBtn;
    private final ExportPartitionedAbNButton exportBtn;
    
    private TANConfiguration currentConfiguration;
    
    private final PartitionedAbNSelectionPanel abnTypeSelectionPanel;
    
    private final ClusterConfigurationConfiguration configConfig;

    public ClusterInternalGraphFrame(
            JFrame parentFrame, 
            ClusterConfigurationConfiguration configConfig,
            DisplayReportsAction displayReportsAction,
            AbNDisplayManager displayListener) {
        
        super(parentFrame, "Tribal Abstraction Network");
        
        this.configConfig = configConfig;
        
        this.displayListener = displayListener;
        
        openReportsBtn = new JButton("Reports and Metrics");
        openReportsBtn.addActionListener( (ae) -> {
            displayReportsAction.displayReports(currentConfiguration);
        });
        
        exportBtn = new ExportPartitionedAbNButton();

        addReportButtonToMenu(openReportsBtn);
        addReportButtonToMenu(exportBtn);

        searchButton = new PartitionedAbNSearchButton(parentFrame, configConfig.createConfigurationFor(null, displayListener).getTextConfiguration());
        
        addToggleableButtonToMenu(searchButton);
        
        this.abnTypeSelectionPanel = new PartitionedAbNSelectionPanel() {

            @Override
            public void showFullClicked() {
                displayClusterTAN(currentConfiguration.getTribalAbstractionNetwork());
            }

            @Override
            public void showBaseClicked() {
                displayBandTAN(currentConfiguration.getTribalAbstractionNetwork());
            }
        };

        this.addOtherOptionsComponent(abnTypeSelectionPanel);
    }

    private void updateHierarchyInfoLabel(ClusterTribalAbstractionNetwork tan) {
        int setCount = tan.getBands().size();
        int clusterCount = tan.getClusters().size();
        int conceptCount = tan.getSourceHierarchy().size();
        
        setHierarchyInfoText(String.format("Bands: %d | Clusters: %d | %s: %d",
                setCount, 
                clusterCount, 
                currentConfiguration.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true),
                conceptCount));
    }

    public void displayClusterTAN(ClusterTribalAbstractionNetwork tan) {
        getAbNExplorationPanel().showLoading();
        
        Thread loadThread = new Thread(() -> {

            AbNPainter painter;
            SinglyRootedNodeLabelCreator<Cluster> labelCreator;
            
            if(tan.isAggregated()) {
                painter = new AggregateTANPainter();
                labelCreator = new AggregateSinglyRootedNodeLabelCreator<>();
            } else {
                painter = new TANPainter();
                labelCreator = new SinglyRootedNodeLabelCreator<>();
            }

            TANConfiguration config = configConfig.createConfigurationFor(tan, displayListener);

            AbstractionNetworkGraph newGraph = new ClusterTANGraph(tan, labelCreator, config);
            
            displayTAN(config, newGraph, painter, true);
        });
        
        loadThread.start();
    }
    
    public final void displayBandTAN(ClusterTribalAbstractionNetwork tan) {
        getAbNExplorationPanel().showLoading();
        
        Thread loadThread = new Thread(() -> {
            PartitionedAbNPainter abnPainter = new PartitionedAbNPainter();
            
            TANConfiguration config = configConfig.createConfigurationFor(tan, displayListener);

            abnPainter.initialize(tan);

            BandTANGraph newGraph = new BandTANGraph(tan, new SinglyRootedNodeLabelCreator<>(), config);

            displayTAN(config, newGraph, abnPainter, false);
        });

        loadThread.start();
    }
    
    private void displayTAN(
            TANConfiguration config,
            AbstractionNetworkGraph<ClusterTribalAbstractionNetwork> graph,
            AbNPainter painter,
            boolean showClusterTAN) {
        
        this.currentConfiguration = config;

        abnTypeSelectionPanel.initialize(config, showClusterTAN);
        exportBtn.initialize(config);
        searchButton.initialize(config);

        SwingUtilities.invokeLater(() -> {
            displayAbstractionNetwork(graph,
                    painter,
                    currentConfiguration,
                    new AggregateableAbNExplorationPanelInitializer((bound) -> {
                        ClusterTribalAbstractionNetwork aggregateTAN = currentConfiguration.getAbstractionNetwork().getAggregated(bound);

                        displayClusterTAN(aggregateTAN);
                    }));

            updateHierarchyInfoLabel(config.getTribalAbstractionNetwork());
        });
    }
}
