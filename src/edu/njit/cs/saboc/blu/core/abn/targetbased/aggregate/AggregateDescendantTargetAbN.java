package edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.targetbased.DescendantTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AggregateDescendantTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris Ochs
 */
public class AggregateDescendantTargetAbN extends DescendantTargetAbN<AggregateTargetGroup> 
        implements AggregateAbstractionNetwork<AggregateTargetGroup, TargetAbstractionNetwork> {
    
    private final TargetAbstractionNetwork nonAggregateSourceTAN;
    
    private final int minBound;
    private final boolean isWeightedAggregated;
    
    public AggregateDescendantTargetAbN(
            TargetAbstractionNetwork aggregateSourceTargetAbN, 
            AggregatedProperty aggregatedProperty,
            TargetAbstractionNetwork nonAggregateTargetAbN,
            TargetAbstractionNetwork<?> subTAN) {
        
        super(aggregateSourceTargetAbN, 
                (Hierarchy<AggregateTargetGroup>)subTAN.getTargetGroupHierarchy(), 
                subTAN.getSourceHierarchy(),                 
                new AggregateDescendantTargetAbNDerivation(
                        aggregateSourceTargetAbN.getDerivation(), 
                        aggregatedProperty,
                        subTAN.getTargetGroupHierarchy().getRoot().getRoot())
        );
        
        
        this.minBound = aggregatedProperty.getBound();
        this.isWeightedAggregated = aggregatedProperty.getWeighted();
        
        this.nonAggregateSourceTAN = nonAggregateTargetAbN;
        
        this.setAggregated(true);
    }
    
    public AggregateDescendantTargetAbN(AggregateDescendantTargetAbN subTAN) {
        
        this(subTAN.getSuperAbN(), 
                subTAN.getAggregatedProperty(),
                subTAN.getNonAggregateSourceAbN(), 
                subTAN);
    }

    @Override
    public TargetAbstractionNetwork getNonAggregateSourceAbN() {
        return nonAggregateSourceTAN;
    }
    
    @Override
    public int getAggregateBound() {
        return minBound;
    }
    
    public boolean isWeightedAggregate() {
        return this.isWeightedAggregated;
    }

    @Override
    public boolean isAggregated() {
        return true;
    }
        
    @Override
    public TargetAbstractionNetwork getAggregated(int smallestNode, boolean isWeightedAggregated) {
        return AggregateTargetAbN.createAggregated(this.getNonAggregateSourceAbN(), new AggregatedProperty(smallestNode, isWeightedAggregated));
    }

    @Override
    public TargetAbstractionNetwork expandAggregateNode(AggregateTargetGroup aggregateNode) {
        return new AggregateTargetAbNGenerator().createExpandedTargetAbN(this, aggregateNode);
    }
    
    @Override
    public AggregateAncestorTargetAbN createAncestorTargetAbN(AggregateTargetGroup source) {
        return AggregateTargetAbN.createAggregateAncestorTargetAbN(
                this.getNonAggregateSourceAbN(), 
                this, 
                source);
    }
    
    @Override
    public AggregateDescendantTargetAbN createDescendantTargetAbN(AggregateTargetGroup root) {
        
        return AggregateTargetAbN.createAggregateDescendantTargetAbN(
                this.getNonAggregateSourceAbN(),
                this,
                root);
    }

    @Override
    public AggregatedProperty getAggregatedProperty() {
        return new AggregatedProperty(this.minBound, this.isWeightedAggregated);
    }

    @Override
    public boolean isWeightedAggregated() {
        return this.isWeightedAggregated;
    }
}