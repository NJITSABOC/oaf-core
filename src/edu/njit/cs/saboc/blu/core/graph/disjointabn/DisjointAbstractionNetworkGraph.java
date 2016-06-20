package edu.njit.cs.saboc.blu.core.graph.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.GroupEntryLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class DisjointAbstractionNetworkGraph extends BluGraph {
    
    public DisjointAbstractionNetworkGraph(JFrame parentFrame, 
            DisjointAbstractionNetwork disjointAbN, 
            GroupEntryLabelCreator labelCreator) {
        
        super(disjointAbN, labelCreator);
    }
}
