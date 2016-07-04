package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DisjointTANFactory implements DisjointAbNFactory<Cluster, DisjointCluster> {

    @Override
    public DisjointCluster createDisjointNode(ConceptHierarchy hierarchy, Set<Cluster> overlaps) {
        return new DisjointCluster(hierarchy, overlaps);
    }
}
