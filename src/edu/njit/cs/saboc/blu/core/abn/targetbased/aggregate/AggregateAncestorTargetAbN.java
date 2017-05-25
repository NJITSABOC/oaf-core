package edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.targetbased.AncestorTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AggregateAncestorTargetAbNDerivation;

/**
 *
 * @author Chris Ochs
 */
public class AggregateAncestorTargetAbN extends AncestorTargetAbN<AggregateTargetGroup> 
        implements AggregateAbstractionNetwork<AggregateTargetGroup, TargetAbstractionNetwork> {
    
    private final TargetAbstractionNetwork nonAggregateSourceTargetAbN;
    
    private final int minBound;
    
    private final boolean weightedAggregate;
    
    public AggregateAncestorTargetAbN(
            TargetAbstractionNetwork aggregateSourceTAN, 
            AggregateTargetGroup sourceGroup,
            int aggregateBound, 
            boolean weightedAggregate,
            TargetAbstractionNetwork nonAggregateSourceTargetAbN,
            TargetAbstractionNetwork subTAN) {
        
        super(aggregateSourceTAN, 
                sourceGroup, 
                subTAN.getTargetGroupHierarchy(),
                subTAN.getSourceHierarchy(), 
                new AggregateAncestorTargetAbNDerivation(
                        aggregateSourceTAN.getDerivation(), 
                        aggregateBound, 
                        weightedAggregate,
                        sourceGroup.getRoot()));
        
        this.minBound = aggregateBound;
        this.weightedAggregate = weightedAggregate;
        
        this.nonAggregateSourceTargetAbN = nonAggregateSourceTargetAbN;
        
        this.setAggregated(true);
    }
    
    public AggregateAncestorTargetAbN(AggregateAncestorTargetAbN subTAN) {
        
        this(subTAN.getSuperAbN(), 
                subTAN.getSelectedRoot(), 
                subTAN.getAggregateBound(), 
                subTAN.isWeightedAggregated(),
                subTAN.getNonAggregateSourceAbN(), 
                subTAN);
    }

    @Override
    public TargetAbstractionNetwork getNonAggregateSourceAbN() {
        return nonAggregateSourceTargetAbN;
    }
    
    @Override
    public int getAggregateBound() {
        return minBound;
    }
    
    public boolean isWeightedAggregated() {
        return this.weightedAggregate;
    }

    @Override
    public boolean isAggregated() {
        return true;
    }
        
    @Override
    public TargetAbstractionNetwork getAggregated(int smallestNode) {
        return AggregateTargetAbN.createAggregated(this.getNonAggregateSourceAbN(), smallestNode, false);
    }

    @Override
    public TargetAbstractionNetwork expandAggregateNode(AggregateTargetGroup group) {        
        return new AggregateTargetAbNGenerator().createExpandedTargetAbN(this, group);
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
        return new AggregatedProperty(this.minBound, this.weightedAggregate);
    }
}