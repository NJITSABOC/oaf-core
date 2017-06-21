package edu.njit.cs.saboc.blu.core.abn.tan.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
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
    private final boolean isWeightedAggregated;
    
    public AggregateAncestorSubTAN(
            ClusterTribalAbstractionNetwork aggregateSourceTAN, 
            AggregateCluster sourceCluster,
            AggregatedProperty aggregatedProperty,
            ClusterTribalAbstractionNetwork nonAggregateRootTAN,
            ClusterTribalAbstractionNetwork subTAN) {
        
        super(aggregateSourceTAN, 
                sourceCluster, 
                subTAN.getBandTAN(), 
                subTAN.getClusterHierarchy(), 
                subTAN.getSourceHierarchy(), 
                new AggregateAncestorSubTANDerivation(
                        aggregateSourceTAN.getDerivation(), 
                        aggregatedProperty, 
                        sourceCluster.getRoot()));
        
        this.minBound = aggregatedProperty.getBound();
        this.nonAggregateSourceTAN = nonAggregateRootTAN;
        this.isWeightedAggregated = aggregatedProperty.getWeighted();
    }
    
    public AggregateAncestorSubTAN(AggregateAncestorSubTAN subTAN) {
        
        this(subTAN.getSuperAbN(), 
                subTAN.getSelectedRoot(), 
                subTAN.getAggregatedProperty(), 
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
    public ClusterTribalAbstractionNetwork getAggregated(int smallestNode, boolean isWeightedAggregated) {
        return AggregateClusterTribalAbstractionNetwork.generateAggregatedClusterTAN(this.getNonAggregateSourceAbN(), new AggregatedProperty(smallestNode, isWeightedAggregated));
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

    @Override
    public AggregatedProperty getAggregatedProperty() {
        return new AggregatedProperty(minBound, isWeightedAggregated);
    }

    @Override
    public boolean isWeightedAggregated() {
        return this.isWeightedAggregated;
    }
}