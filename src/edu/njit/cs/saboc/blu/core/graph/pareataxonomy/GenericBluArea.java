package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import java.awt.Rectangle;

/**
 *
 * @author Chris O
 */
public class GenericBluArea<AREA_T extends Area> extends PartitionedNodeEntry {

    public GenericBluArea(AREA_T area, BluGraph g, int aX, GraphLevel parent, Rectangle prefBounds) {
        super(area, g, aX, parent, prefBounds);
    }
    
    public AREA_T getArea() {
        return (AREA_T)container;
    }
}
