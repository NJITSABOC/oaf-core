package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AncestorSubTAN extends SubTAN {
    
    private final Cluster sourceCluster;
    
    public AncestorSubTAN(
            ClusterTribalAbstractionNetwork sourceTAN,
            Cluster sourceCluster,
            BandTribalAbstractionNetwork bandTan,
            Hierarchy<Cluster> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        super(sourceTAN, bandTan, clusterHierarchy, sourceHierarchy);
        
        this.sourceCluster = sourceCluster;
    }
    
    public Cluster getSourceCluster() {
        return sourceCluster;
    }
}
