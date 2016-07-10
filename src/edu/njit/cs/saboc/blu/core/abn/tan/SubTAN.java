package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class SubTAN extends ClusterTribalAbstractionNetwork {
    
    private final ClusterTribalAbstractionNetwork sourceTAN;
    
    public SubTAN(
            ClusterTribalAbstractionNetwork sourceTAN,
            BandTribalAbstractionNetwork bandTan,
            Hierarchy<Cluster> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        super(bandTan, clusterHierarchy, sourceHierarchy);
        
        this.sourceTAN = sourceTAN;
    }
    
    public ClusterTribalAbstractionNetwork getSourceTAN() {
        return sourceTAN;
    }
}
