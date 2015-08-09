package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class GenericBluPArea<PAREA_T extends GenericPArea, 
        REGIONENTRY_T extends GenericBluRegion> extends GenericGroupEntry<PAREA_T> {
    
    public GenericBluPArea(PAREA_T parea, BluGraph g, REGIONENTRY_T r, int pX, GraphGroupLevel parent, ArrayList<GraphEdge> ie) {
        super(parea, g, r, pX, parent, ie);
    }
}
