package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.PAreaTaxonomyGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.AggregateableAbNExplorationPanelInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateSinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.pareataxonomy.AggregatePAreaTaxonomyPainter;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.GraphFrameInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.TaskBarPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.taskbarpanels.PartitionedAbNTaskBarPanel;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyInitializer implements GraphFrameInitializer<PAreaTaxonomy, PAreaTaxonomyConfiguration> {

    @Override
    public AbstractionNetworkGraph getGraph(
            JFrame parentFrame, 
            PAreaTaxonomyConfiguration config, 
            SinglyRootedNodeLabelCreator labelCreator) {
        
        return new PAreaTaxonomyGraph(parentFrame, config.getPAreaTaxonomy(), labelCreator, config);
    }

    @Override
    public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, 
            PAreaTaxonomyConfiguration config) {
        
        return new PartitionedAbNTaskBarPanel(graphFrame, config);
    }

    @Override
    public AbNPainter getAbNPainter(PAreaTaxonomy abn) {
        if (abn.isAggregated()) {
            return new AggregatePAreaTaxonomyPainter();
        } else {
            return new AbNPainter();
        }
    }

    @Override
    public SinglyRootedNodeLabelCreator getLabelCreator(PAreaTaxonomy abn) {
        if (abn.isAggregated()) {
            return new AggregateSinglyRootedNodeLabelCreator<>();
        } else {
            return new SinglyRootedNodeLabelCreator<>();
        }
    }

    @Override
    public AbNExplorationPanelGUIInitializer getExplorationGUIInitializer(PAreaTaxonomyConfiguration config) {
        return new AggregateableAbNExplorationPanelInitializer((bound) -> {
            PAreaTaxonomy aggregateTaxonomy = config.getPAreaTaxonomy().getAggregated(bound);
            config.getUIConfiguration().getAbNDisplayManager().displayPAreaTaxonomy(aggregateTaxonomy);
        });
    }
}
