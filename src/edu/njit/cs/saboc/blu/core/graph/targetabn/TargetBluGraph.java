package edu.njit.cs.saboc.blu.core.graph.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class TargetBluGraph extends BluGraph {

    public TargetBluGraph(
            final JFrame parentFrame, 
            final TargetAbstractionNetwork targetAbN, 
            final SinglyRootedNodeLabelCreator labelCreator) {

        super(targetAbN, labelCreator);

        layout = new TargetAbNLayout(this, targetAbN);

        ((TargetAbNLayout)layout).doLayout();
    }
}
