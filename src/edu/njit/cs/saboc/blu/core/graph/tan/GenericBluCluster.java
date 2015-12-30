package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericCluster;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericGroupEntry;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class GenericBluCluster <CLUSTER_T extends GenericCluster> extends GenericGroupEntry<CLUSTER_T> {
    public GenericBluCluster(CLUSTER_T cluster, BluGraph g, GenericBluBandPartition r, int pX, GraphGroupLevel parent, ArrayList<GraphEdge> ie) {
        super(cluster, g, r, pX, parent, ie);
    }

    public CLUSTER_T getCluster() {
        return getGroup();
    }
}
