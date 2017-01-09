package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
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
    
    private final TargetAbstractionNetwork sourceTargetAbN;
    
    private final int minBound;

    public AggregateTargetAbN(TargetAbstractionNetwork sourceTargetAbN,
            int minBound,
            Hierarchy<T> groupHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        super(groupHierarchy, sourceHierarchy);
        
        this.sourceTargetAbN = sourceTargetAbN;
        this.minBound = minBound;
    }

    @Override
    public TargetAbstractionNetwork expandAggregateNode(AggregateTargetGroup node) {
        throw new UnsupportedOperationException("Not supported yet."); 
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
}
