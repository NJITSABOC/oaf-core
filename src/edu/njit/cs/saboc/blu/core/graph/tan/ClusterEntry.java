package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.graph.BluGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class ClusterEntry extends SinglyRootedNodeEntry {
    public ClusterEntry(Cluster cluster, BluGraph g, GenericBluBandPartition r, int pX, GraphGroupLevel parent, ArrayList<GraphEdge> ie) {
        super(cluster, g, r, pX, parent, ie);
    }

    public Cluster getCluster() {
        return (Cluster)super.getNode();
    }
}
