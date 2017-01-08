package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.RootedSubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class RootSubTAN<T extends Cluster> extends ClusterTribalAbstractionNetwork<T> 
        implements RootedSubAbstractionNetwork<T, ClusterTribalAbstractionNetwork<T>> {
    
    private final ClusterTribalAbstractionNetwork<T> sourceTAN;
    
    public RootSubTAN(
            ClusterTribalAbstractionNetwork sourceTAN,
            BandTribalAbstractionNetwork bandTan,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        super(bandTan, clusterHierarchy, sourceHierarchy);
        
        this.sourceTAN = sourceTAN;
    }

    @Override
    public T getSelectedRoot() {
        return this.getClusterHierarchy().getRoot();
    }

    @Override
    public ClusterTribalAbstractionNetwork<T> getSuperAbN() {
        return sourceTAN;
    }
}
