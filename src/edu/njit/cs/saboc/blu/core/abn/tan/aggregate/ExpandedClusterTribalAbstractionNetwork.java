package edu.njit.cs.saboc.blu.core.abn.tan.aggregate;

import edu.njit.cs.saboc.blu.core.abn.SubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.BandTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class ExpandedClusterTribalAbstractionNetwork extends ClusterTribalAbstractionNetwork 
        implements SubAbstractionNetwork<ClusterTribalAbstractionNetwork> {
    
    private final ClusterTribalAbstractionNetwork aggregateSourceTAN;
    private final AggregateCluster aggregateCluster;
    
    public ExpandedClusterTribalAbstractionNetwork(
            ClusterTribalAbstractionNetwork aggregateSourceTAN,
            AggregateCluster aggregateCluster,
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<Cluster> clusterHierarchy,
            Hierarchy<Concept> conceptHierarchy) {

        super(bandTAN, clusterHierarchy, conceptHierarchy);
        
        this.aggregateSourceTAN = aggregateSourceTAN;
        this.aggregateCluster = aggregateCluster;
    }
    
    public ExpandedClusterTribalAbstractionNetwork(ExpandedClusterTribalAbstractionNetwork expandedTAN) {
        this(expandedTAN.getSuperAbN(), 
                expandedTAN.getAggregatePArea(), 
                expandedTAN.getBandTAN(), 
                expandedTAN.getClusterHierarchy(), 
                expandedTAN.getSourceHierarchy());
    }

    @Override
    public ClusterTribalAbstractionNetwork getSuperAbN() {
        return aggregateSourceTAN;
    }
    
    public AggregateCluster getAggregatePArea() {
        return aggregateCluster;
    }
}