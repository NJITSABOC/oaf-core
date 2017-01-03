package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomyGraph<T extends PAreaTaxonomy> extends AbstractionNetworkGraph<T> {

    public PAreaTaxonomyGraph(
            final JFrame parentFrame, 
            final T taxonomy, 
            final SinglyRootedNodeLabelCreator labelCreator,
            final PAreaTaxonomyConfiguration config) {
        
        super(taxonomy, labelCreator);
        
        super.setAbstractionNetworkLayout(new NoRegionsPAreaTaxonomyLayout(this, taxonomy, config));
    }

    public T getPAreaTaxonomy() {
        return (T)getAbstractionNetwork();
    }
}