package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DisjointCluster extends DisjointNode<Cluster> {
    public DisjointCluster(ConceptHierarchy concepts, Set<Cluster> overlaps) {
        super(concepts, overlaps);
    }
}