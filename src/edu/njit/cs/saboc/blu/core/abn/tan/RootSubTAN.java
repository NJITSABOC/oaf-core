package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class RootSubTAN extends SubTAN {
    public RootSubTAN(
            ClusterTribalAbstractionNetwork sourceTAN,
            BandTribalAbstractionNetwork bandTan,
            Hierarchy<Cluster> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        super(sourceTAN, bandTan, clusterHierarchy, sourceHierarchy);
    }
    
    public Cluster getSelectedRootCluster() {
        return super.getClusterHierarchy().getRoot();
    }
}
