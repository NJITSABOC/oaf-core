package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.AreaTaxonomyGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.PartitionedAbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public abstract class AreaTaxonomyInitializer extends PAreaTaxonomyInitializer {
    
    @Override
    public PAreaInitializerType getInitializerType() {
        return PAreaInitializerType.AreaTaxonomy;
    }

    @Override
    public AbNPainter getAbNPainter(PAreaTaxonomy abn) {
        PartitionedAbNPainter painter = new PartitionedAbNPainter();
        painter.initialize(abn);
        
        return painter;
    }

    @Override
    public AbstractionNetworkGraph getGraph(JFrame parentFrame, PAreaTaxonomyConfiguration config, SinglyRootedNodeLabelCreator labelCreator) {
        return new AreaTaxonomyGraph(parentFrame, config.getPAreaTaxonomy(), labelCreator, config);
    }
}