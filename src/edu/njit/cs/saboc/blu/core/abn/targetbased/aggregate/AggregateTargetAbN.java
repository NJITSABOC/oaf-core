package edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AggregateTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * A target abstraction network that has been aggregated according to the given
 * bound
 * 
 * @author Chris O
 * @param <T>
 */
public class AggregateTargetAbN<T extends TargetGroup> extends TargetAbstractionNetwork<T> 
        implements AggregateAbstractionNetwork<AggregateTargetGroup, TargetAbstractionNetwork> {
    
    public static final TargetAbstractionNetwork createAggregated(
            TargetAbstractionNetwork nonAggregated, 
            int smallestNode) {
        
        AggregateTargetAbNGenerator generator = new AggregateTargetAbNGenerator();
        
        return generator.createAggregateTargetAbN(nonAggregated, 
                    new TargetAbstractionNetworkGenerator(), 
                    new AggregateAbNGenerator<>(), 
                    smallestNode);
    }
    
    public static final TargetAbstractionNetwork createExpanded(
            AggregateTargetAbN aggregateAbN, 
            AggregateTargetGroup selectedGroup) {
        
        AggregateTargetAbNGenerator generator = new AggregateTargetAbNGenerator();
        
        return generator.createExpandedTargetAbN(aggregateAbN, selectedGroup);
    }
    
    private final TargetAbstractionNetwork sourceTargetAbN;
    
    private final int minBound;

    public AggregateTargetAbN(
            TargetAbstractionNetwork sourceTargetAbN,
            int minBound,
            Hierarchy<T> groupHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        super(groupHierarchy, 
                sourceHierarchy, 
                new AggregateTargetAbNDerivation(sourceTargetAbN.getDerivation(), minBound));
        
        this.sourceTargetAbN = sourceTargetAbN;
        this.minBound = minBound;
    }
    
    public AggregateTargetAbN(AggregateTargetAbN base) {
        
        this(base.getNonAggregateSourceAbN(), 
                base.getAggregateBound(), 
                base.getNodeHierarchy(), 
                base.getSourceHierarchy());
    }

    @Override
    public TargetAbstractionNetwork expandAggregateNode(AggregateTargetGroup node) {
        AggregateTargetAbNGenerator generator = new AggregateTargetAbNGenerator();

        return generator.createExpandedTargetAbN(this, node);
    }

    @Override
    public TargetAbstractionNetwork getNonAggregateSourceAbN() {
        return sourceTargetAbN;
    }
    
    @Override
    public int getAggregateBound() {
        return minBound;
    }
    
    @Override
    public TargetAbstractionNetwork getAggregated(int smallestNode) {
        return AggregateTargetAbN.createAggregated(getNonAggregateSourceAbN(), smallestNode);
    }
    
    public TargetAbstractionNetwork expandAggregateTargetGroup(AggregateTargetGroup targetGroup) {
        return AggregateTargetAbN.createExpanded(this, targetGroup);
    }
}
