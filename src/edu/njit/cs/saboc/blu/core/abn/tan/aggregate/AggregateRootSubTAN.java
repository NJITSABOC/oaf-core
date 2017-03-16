package edu.njit.cs.saboc.blu.core.abn.tan.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.RootSubTAN;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateRootSubTANDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 * An aggregate TAN that consists of a chosen aggregate cluster and all of its
 * descendant aggregate clusters
 * 
 * @author Chris O
 */
public class AggregateRootSubTAN extends RootSubTAN<AggregateCluster> 
        implements AggregateAbstractionNetwork<AggregateCluster, ClusterTribalAbstractionNetwork> {
    
    private final ClusterTribalAbstractionNetwork nonAggregateSourceTAN;
    private final int minBound;
    
    public AggregateRootSubTAN(
            ClusterTribalAbstractionNetwork aggregateSourceTAN, 
            int aggregateBound, 
            ClusterTribalAbstractionNetwork nonAggregateRootTAN,
            ClusterTribalAbstractionNetwork<?> subTAN) {
        
        super(aggregateSourceTAN, 
                subTAN.getBandTAN(), 
                (Hierarchy<AggregateCluster>)subTAN.getClusterHierarchy(), 
                subTAN.getSourceHierarchy(), 
                
                new AggregateRootSubTANDerivation(
                        aggregateSourceTAN.getDerivation(), 
                        aggregateBound, 
                        subTAN.getClusterHierarchy().getRoot().getRoot())
        );
        
        
        this.minBound = aggregateBound;
        this.nonAggregateSourceTAN = nonAggregateRootTAN;
    }
    
    public AggregateRootSubTAN(AggregateRootSubTAN subTAN) {
        this(subTAN.getSuperAbN(), 
                subTAN.getAggregateBound(), 
                subTAN.getNonAggregateSourceAbN(), 
                subTAN);
    }

    @Override
    public ClusterTribalAbstractionNetwork getNonAggregateSourceAbN() {
        return nonAggregateSourceTAN;
    }
    
    @Override
    public int getAggregateBound() {
        return minBound;
    }

    @Override
    public boolean isAggregated() {
        return true;
    }
        
    @Override
    public ClusterTribalAbstractionNetwork getAggregated(int smallestNode) {
        return AggregateClusterTribalAbstractionNetwork.generateAggregatedClusterTAN(this.getNonAggregateSourceAbN(), smallestNode);
    }

    @Override
    public ClusterTribalAbstractionNetwork expandAggregateNode(AggregateCluster cluster) {
        return new AggregateTANGenerator().createExpandedTAN(
                this,
                cluster,
                new TribalAbstractionNetworkGenerator());
    }

    @Override
    public AggregateAncestorSubTAN createAncestorTAN(AggregateCluster source) {
        return AggregateClusterTribalAbstractionNetwork.generateAggregateAncestorSubTAN(
                this.getNonAggregateSourceAbN(),
                this.getSuperAbN(),
                source);
    }

    @Override
    public AggregateRootSubTAN createRootSubTAN(AggregateCluster root) {
        return AggregateClusterTribalAbstractionNetwork.generateAggregateRootSubTAN(
                this.getNonAggregateSourceAbN(),
                this.getSuperAbN(),
                (AggregateCluster) root);
    }

}
