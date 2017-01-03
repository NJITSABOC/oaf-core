package edu.njit.cs.saboc.blu.core.graph.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class DisjointAbstractionNetworkGraph extends AbstractionNetworkGraph {
    
    public DisjointAbstractionNetworkGraph(JFrame parentFrame, 
            DisjointAbstractionNetwork disjointAbN, 
            SinglyRootedNodeLabelCreator labelCreator) {
        
        super(disjointAbN, labelCreator);
    }
}
