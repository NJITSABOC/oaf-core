package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.GroupEntryLabelCreator;

/**
 *
 * @author Chris O
 */
public class ClusterBluGraph extends BluGraph {
    
    public ClusterBluGraph(final TribalAbstractionNetwork tan, GroupEntryLabelCreator labelCreator) {
        super(tan, labelCreator);
    }
}
