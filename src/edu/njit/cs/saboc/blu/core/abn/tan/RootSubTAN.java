package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class RootSubTAN<T extends Cluster> extends SubTAN<T> {
    public RootSubTAN(
            ClusterTribalAbstractionNetwork sourceTAN,
            BandTribalAbstractionNetwork bandTan,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        super(sourceTAN, bandTan, clusterHierarchy, sourceHierarchy);
    }
    
    public T getSelectedRootCluster() {
        return super.getClusterHierarchy().getRoot();
    }
}
