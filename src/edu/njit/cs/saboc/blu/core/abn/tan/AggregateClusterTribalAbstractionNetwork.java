package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AggregateClusterTribalAbstractionNetwork extends ClusterTribalAbstractionNetwork<AggregateCluster> {
    
    private final ClusterTribalAbstractionNetwork sourceTAN;
    
    private final int minBound;
    
    public AggregateClusterTribalAbstractionNetwork(
            ClusterTribalAbstractionNetwork sourceTAN,
            int minBound,
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<AggregateCluster> clusterHierarchy,
            Hierarchy<Concept> conceptHierarchy) {
        
        super(bandTAN, clusterHierarchy, conceptHierarchy);
        
        this.sourceTAN = sourceTAN;
        this.minBound = minBound;
    }
    
    public ClusterTribalAbstractionNetwork getSourceTAN() {
        return sourceTAN;
    }
    
    public int getMinBound() {
        return minBound;
    }
}
