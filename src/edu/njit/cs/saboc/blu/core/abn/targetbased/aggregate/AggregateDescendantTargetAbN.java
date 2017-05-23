package edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
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
    
    public AggregateDescendantTargetAbN(
            TargetAbstractionNetwork aggregateSourceTargetAbN, 
            int aggregateBound, 
            TargetAbstractionNetwork nonAggregateTargetAbN,
            TargetAbstractionNetwork<?> subTAN) {
        
        super(aggregateSourceTargetAbN, 
                (Hierarchy<AggregateTargetGroup>)subTAN.getTargetGroupHierarchy(), 
                subTAN.getSourceHierarchy(), 
                
                new AggregateDescendantTargetAbNDerivation(
                        aggregateSourceTargetAbN.getDerivation(), 
                        aggregateBound, 
                        subTAN.getTargetGroupHierarchy().getRoot().getRoot())
        );
        
        
        this.minBound = aggregateBound;
        this.nonAggregateSourceTAN = nonAggregateTargetAbN;
        
        this.setAggregated(true);
    }
    
    public AggregateDescendantTargetAbN(AggregateDescendantTargetAbN subTAN) {
        this(subTAN.getSuperAbN(), 
                subTAN.getAggregateBound(), 
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

    @Override
    public boolean isAggregated() {
        return true;
    }
        
    @Override
    public TargetAbstractionNetwork getAggregated(int smallestNode) {
        return AggregateTargetAbN.createAggregated(this.getNonAggregateSourceAbN(), smallestNode);
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
}