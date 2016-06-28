package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class PAreaBluGraph extends BluGraph {

    public PAreaBluGraph(
            final JFrame parentFrame, 
            final PAreaTaxonomy taxonomy, 
            final SinglyRootedNodeLabelCreator labelCreator,
            final PAreaTaxonomyConfiguration config) {
        
        super(taxonomy, labelCreator);

        layout = new NoRegionsPAreaTaxonomyLayout(this, taxonomy, config);
        
        ((NoRegionsPAreaTaxonomyLayout) layout).doLayout();
    }

    public PAreaTaxonomy getPAreaTaxonomy() {
        return (PAreaTaxonomy) getAbstractionNetwork();
    }
}