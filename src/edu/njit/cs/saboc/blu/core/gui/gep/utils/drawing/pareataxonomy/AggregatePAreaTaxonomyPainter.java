package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.pareataxonomy;

import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateNodePainter;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaTaxonomyPainter extends AbNPainter {
    public void paintGroupAtPoint(Graphics2D g2d, SinglyRootedNodeEntry nodeEntry, Point p, double scale) {
        AggregateNodePainter.paintGroupAtPoint(g2d, nodeEntry, p, scale);
    }
}
