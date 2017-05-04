package edu.njit.cs.saboc.blu.core.abn.tan.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 * A generator for creating aggregate tribal abstraction networks
 * 
 * @author Chris O
 */
public class AggregateTANGenerator {

    /**
     * Creates an aggregate TAN with the given bound
     * 
     * @param sourceTAN
     * @param generator
     * @param aggregateGenerator
     * @param min
     * @return 
     */
    public ClusterTribalAbstractionNetwork createAggregateTAN(
            final ClusterTribalAbstractionNetwork sourceTAN,
            final TribalAbstractionNetworkGenerator generator,
            final AggregateAbNGenerator<Cluster, AggregateCluster> aggregateGenerator,
            final int min,
            final boolean weightedAggregated) {

        if (min == 1) {
            return sourceTAN;
        }

        Hierarchy<AggregateCluster> aggregateClusterHierarchy = 
                aggregateGenerator.createAggregateAbN(
                        new AggregateTANFactory(),
                        sourceTAN.getClusterHierarchy(),
                        sourceTAN.getSourceHierarchy(),
                        min,
                        weightedAggregated);

        Hierarchy<Cluster> clusterHierarchy = (Hierarchy<Cluster>) (Hierarchy<?>) aggregateClusterHierarchy;

        ClusterTribalAbstractionNetwork tan = generator.createTANFromClusters(
                clusterHierarchy, 
                sourceTAN.getSourceHierarchy(),
                sourceTAN.getSourceFactory());

        return new AggregateClusterTribalAbstractionNetwork(
                sourceTAN,
                min,
                tan.getBandTAN(),
                tan.getClusterHierarchy(),
                tan.getSourceHierarchy());
    }
    
    
    /**
     * Creates an expanded TAN from a given aggregate cluster. The expanded TAN
     * consists of the subhierarchy of clusters summarized by an aggregate cluster
     * 
     * @param sourceAggregateTAN
     * @param aggregateCluster
     * @param generator
     * @return 
     */

    public ExpandedClusterTribalAbstractionNetwork createExpandedTAN(
            ClusterTribalAbstractionNetwork sourceAggregateTAN,
            AggregateCluster aggregateCluster,
            TribalAbstractionNetworkGenerator generator) {

        ClusterTribalAbstractionNetwork tan = generator.createTANFromClusters(
                aggregateCluster.getAggregatedHierarchy(), 
                sourceAggregateTAN.getSourceHierarchy(),
                sourceAggregateTAN.getSourceFactory());

        ExpandedClusterTribalAbstractionNetwork expandedTAN = new ExpandedClusterTribalAbstractionNetwork(
                sourceAggregateTAN,
                aggregateCluster,
                tan.getBandTAN(),
                tan.getClusterHierarchy(),
                tan.getSourceHierarchy());

        return expandedTAN;
    }
}
