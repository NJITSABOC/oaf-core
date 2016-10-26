package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris O
 */
public class AggregateTANGenerator {

    public ClusterTribalAbstractionNetwork createAggregatePAreaTaxonomy(
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
                        min);

        Hierarchy<Cluster> clusterHierarchy = (Hierarchy<Cluster>) (Hierarchy<?>) aggregateClusterHierarchy;

        ClusterTribalAbstractionNetwork tan = generator.createTANFromClusters(clusterHierarchy, sourceTAN.getSourceFactory());

        AggregateClusterTribalAbstractionNetwork aggregateTaxonomy = new AggregateClusterTribalAbstractionNetwork(
                sourceTAN,
                min,
                tan.getBandTAN(),
                tan.getClusterHierarchy(),
                tan.getSourceHierarchy());

        aggregateTaxonomy.setAggregated(true);

        return aggregateTaxonomy;
    }

    public ExpandedClusterTribalAbstractionNetwork createExpandedSubtaxonomy(
            AggregateClusterTribalAbstractionNetwork sourceAggregateTAN,
            AggregateCluster aggregateCluster,
            TribalAbstractionNetworkGenerator generator) {

        ClusterTribalAbstractionNetwork tan = generator.createTANFromClusters(
                aggregateCluster.getAggregatedHierarchy(), 
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
