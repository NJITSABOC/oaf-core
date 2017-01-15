package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.tan.ClusterTANGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.AggregateableAbNExplorationPanelInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateSinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.tan.AggregateTANPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.tan.TANPainter;
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

    @Override
    public AbstractionNetworkGraph getGraph(JFrame parentFrame, TANConfiguration config, SinglyRootedNodeLabelCreator labelCreator) {
        return new ClusterTANGraph(config.getAbstractionNetwork(), labelCreator, config);
    }

    @Override
    public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, TANConfiguration config) {
        return new PartitionedAbNTaskBarPanel(graphFrame, config);
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
