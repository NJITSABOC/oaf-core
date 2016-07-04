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
public class AreaEntry extends PartitionedNodeEntry {

    public AreaEntry(Area area, BluGraph graph, int aX, GraphLevel parent, Rectangle prefBounds) {
        super(area, graph, aX, parent, prefBounds);
    }
    
    public void addRegionEntry(RegionEntry regionEntry) {
        super.addPartitionEntry(regionEntry);
    }
    
    public Area getArea() {
        return (Area)super.getNode();
    }
}
