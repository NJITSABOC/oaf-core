package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AggregateClusterTribalAbstractionNetwork extends ClusterTribalAbstractionNetwork<AggregateCluster> 
        implements AggregateAbstractionNetwork<AggregateCluster, ClusterTribalAbstractionNetwork> {
    
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
    
    public ExpandedClusterTribalAbstractionNetwork createExpandedTAN(AggregateCluster cluster) {
        
        AggregateTANGenerator generator = new AggregateTANGenerator();
        
        return generator.createExpandedTAN(this, 
                cluster,
                new TribalAbstractionNetworkGenerator());
    }
    
    @Override
    public ClusterTribalAbstractionNetwork getAggregated(int smallestNode) {
        
        AggregateTANGenerator generator = new AggregateTANGenerator();
        
        ClusterTribalAbstractionNetwork aggregateTAN = generator.createAggregateTAN(
            this.getNonAggregateSource(), 
            new TribalAbstractionNetworkGenerator(),
            new AggregateAbNGenerator<>(),
            smallestNode);

        return aggregateTAN;
    }

    @Override
    public ClusterTribalAbstractionNetwork expandAggregateNode(AggregateCluster node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ClusterTribalAbstractionNetwork getNonAggregateSource() {
        return sourceTAN;
    }
    
    @Override
    public int getAggregateBound() {
        return minBound;
    }
}
