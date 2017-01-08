package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.RootedSubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AncestorSubTAN<T extends Cluster> extends ClusterTribalAbstractionNetwork<T> 
        implements RootedSubAbstractionNetwork<T, ClusterTribalAbstractionNetwork<T>> {
    
    private final ClusterTribalAbstractionNetwork sourceTAN;
    private final T sourceCluster;
    
    public AncestorSubTAN(
            ClusterTribalAbstractionNetwork sourceTAN,
            T sourceCluster,
            BandTribalAbstractionNetwork bandTan,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        super(bandTan, clusterHierarchy, sourceHierarchy);
        
        this.sourceTAN = sourceTAN;
        this.sourceCluster = sourceCluster;
    }
    
    public AncestorSubTAN(AncestorSubTAN<T> subTAN) {
        this(subTAN.getSuperAbN(), 
                subTAN.getSelectedRoot(), 
                subTAN.getBandTAN(), 
                subTAN.getClusterHierarchy(), 
                subTAN.getSourceHierarchy());
    }

    @Override
    public T getSelectedRoot() {
        return sourceCluster;
    }

    @Override
    public ClusterTribalAbstractionNetwork getSuperAbN() {
        return sourceTAN;
    }
}
