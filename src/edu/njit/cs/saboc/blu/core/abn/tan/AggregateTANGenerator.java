package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris O
 */
public class AggregateTANGenerator {

    public ClusterTribalAbstractionNetwork createAggregateTAN(
            final ClusterTribalAbstractionNetwork sourceTAN,
            final TribalAbstractionNetworkGenerator generator,
            final AggregateAbNGenerator<Cluster, AggregateCluster> aggregateGenerator,
            final int min) {

        if (min == 1) {
            return sourceTAN;
        }

        Hierarchy<AggregateCluster> aggregateClusterHierarchy = 
                aggregateGenerator.createReducedAbN(
                        new AggregateTANFactory(),
                        sourceTAN.getClusterHierarchy(),
                        sourceTAN.getSourceHierarchy(),
                        min);

        Hierarchy<Cluster> clusterHierarchy = (Hierarchy<Cluster>) (Hierarchy<?>) aggregateClusterHierarchy;

        ClusterTribalAbstractionNetwork tan = generator.createTANFromClusters(
                clusterHierarchy, 
                sourceTAN.getSourceHierarchy(),
                sourceTAN.getSourceFactory());

        AggregateClusterTribalAbstractionNetwork aggregateTaxonomy = new AggregateClusterTribalAbstractionNetwork(
                sourceTAN,
                min,
                tan.getBandTAN(),
                tan.getClusterHierarchy(),
                tan.getSourceHierarchy());

        aggregateTaxonomy.setAggregated(true);

        return aggregateTaxonomy;
    }

    public ExpandedClusterTribalAbstractionNetwork createExpandedTAN(
            AggregateClusterTribalAbstractionNetwork sourceAggregateTAN,
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
