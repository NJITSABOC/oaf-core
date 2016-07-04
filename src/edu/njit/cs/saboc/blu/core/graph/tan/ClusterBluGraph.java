package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;

/**
 *
 * @author Chris O
 */
public class ClusterBluGraph extends BluGraph {
    
    public ClusterBluGraph(final ClusterTribalAbstractionNetwork tan, SinglyRootedNodeLabelCreator labelCreator, TANConfiguration config) {
        super(tan, labelCreator);

        layout = new TANLayout(this, tan, config);

        ((TANLayout) layout).doLayout();
    }
}
