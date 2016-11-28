package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.tan;

import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateNodePainter;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Chris O
 */
public class AggregateTANPainter extends TANPainter {
    
    @Override
    public void paintGroupAtPoint(Graphics2D g2d, SinglyRootedNodeEntry group, Point p, double scale) {
        AggregateNodePainter.paintGroupAtPoint(g2d, group, p, scale);
    }
}