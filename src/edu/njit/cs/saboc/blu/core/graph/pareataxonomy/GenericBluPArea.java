package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class GenericBluPArea<PAREA_T extends PArea, 
        REGIONENTRY_T extends GenericBluRegion> extends SinglyRootedNodeEntry<PAREA_T> {
    
    public GenericBluPArea(PAREA_T parea, BluGraph g, REGIONENTRY_T r, int pX, GraphGroupLevel parent, ArrayList<GraphEdge> ie) {
        super(parea, g, r, pX, parent, ie);
    }
}
