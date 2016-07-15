package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AncestorSubTAN<T extends Cluster> extends SubTAN<T> {
    
    private final T sourceCluster;
    
    public AncestorSubTAN(
            ClusterTribalAbstractionNetwork sourceTAN,
            T sourceCluster,
            BandTribalAbstractionNetwork bandTan,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        super(sourceTAN, bandTan, clusterHierarchy, sourceHierarchy);
        
        this.sourceCluster = sourceCluster;
    }
    
    public T getSourceCluster() {
        return sourceCluster;
    }
}
