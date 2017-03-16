package edu.njit.cs.saboc.blu.core.abn.tan.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.AncestorSubTAN;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AggregateAncestorSubTANDerivation;

/**
 * An aggregate TAN consisting of a chosen aggregate cluster and 
 * all of its ancestor aggregate clusters
 * 
 * @author Chris O
 */
public class AggregateAncestorSubTAN extends AncestorSubTAN<AggregateCluster> 
        implements AggregateAbstractionNetwork<AggregateCluster, ClusterTribalAbstractionNetwork> {
    
    private final ClusterTribalAbstractionNetwork nonAggregateSourceTAN;
    private final int minBound;
    
    public AggregateAncestorSubTAN(
            ClusterTribalAbstractionNetwork aggregateSourceTAN, 
            AggregateCluster sourceCluster,
            int aggregateBound, 
            ClusterTribalAbstractionNetwork nonAggregateRootTAN,
            ClusterTribalAbstractionNetwork subTAN) {
        
        super(aggregateSourceTAN, 
                sourceCluster, 
                subTAN.getBandTAN(), 
                subTAN.getClusterHierarchy(), 
                subTAN.getSourceHierarchy(), 
                new AggregateAncestorSubTANDerivation(
                        aggregateSourceTAN.getDerivation(), 
                        aggregateBound, 
                        sourceCluster.getRoot()));
        
        this.minBound = aggregateBound;
        this.nonAggregateSourceTAN = nonAggregateRootTAN;
    }
    
    public AggregateAncestorSubTAN(AggregateAncestorSubTAN subTAN) {
        
        this(subTAN.getSuperAbN(), 
                subTAN.getSelectedRoot(), 
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
                root);
    }
    
}