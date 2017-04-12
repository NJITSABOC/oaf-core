package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.tan.ClusterTANGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.AggregateableAbNExplorationPanelInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateSinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.tan.AggregateTANPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.tan.TANPainter;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PartitionedAbNSelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.GraphFrameInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.TaskBarPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.taskbarpanels.PartitionedAbNTaskBarPanel;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public abstract class TANInitializer implements GraphFrameInitializer<ClusterTribalAbstractionNetwork, TANConfiguration> {

    public static enum TANInitializerType {
        ClusterTAN,
        BandTAN
    }
    
    public TANInitializerType getInitializerType() {
        return TANInitializerType.ClusterTAN;
    }
    
    @Override
    public AbstractionNetworkGraph getGraph(JFrame parentFrame, TANConfiguration config, SinglyRootedNodeLabelCreator labelCreator) {
        return new ClusterTANGraph(config.getAbstractionNetwork(), labelCreator, config);
    }

    @Override
    public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, TANConfiguration config) {
        PartitionedAbNTaskBarPanel taskBar = new PartitionedAbNTaskBarPanel(graphFrame, config);
        
        PartitionedAbNSelectionPanel abnTypeSelectionPanel = new PartitionedAbNSelectionPanel() {

            @Override
            public void showFullClicked() {
                config.getUIConfiguration().getAbNDisplayManager().displayTribalAbstractionNetwork(config.getAbstractionNetwork());
            }

            @Override
            public void showBaseClicked() {
                config.getUIConfiguration().getAbNDisplayManager().displayBandTribalAbstractionNetwork(config.getAbstractionNetwork());
            }
        };
        
        abnTypeSelectionPanel.initialize(config, getInitializerType().equals(TANInitializerType.ClusterTAN));
        
        taskBar.addOtherOptionsComponent(abnTypeSelectionPanel);
        
        return taskBar;
    }

    @Override
    public AbNPainter getAbNPainter(ClusterTribalAbstractionNetwork abn) {
        if (abn.isAggregated()) {
            return new AggregateTANPainter();
        } else {
            return new TANPainter();
        }
    }

    @Override
    public SinglyRootedNodeLabelCreator getLabelCreator(ClusterTribalAbstractionNetwork abn) {
        if (abn.isAggregated()) {
            return new AggregateSinglyRootedNodeLabelCreator<>();
        } else {
            return new SinglyRootedNodeLabelCreator<>();
        }
    }

    @Override
    public AbNExplorationPanelGUIInitializer getExplorationGUIInitializer(TANConfiguration config) {
        return new AggregateableAbNExplorationPanelInitializer( (bound) -> {
            
            ClusterTribalAbstractionNetwork aggregateTAN = config.getAbstractionNetwork().getAggregated(bound);
            config.getUIConfiguration().getAbNDisplayManager().displayTribalAbstractionNetwork(aggregateTAN);
        });
    }
}
