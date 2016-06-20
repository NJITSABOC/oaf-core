package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;

/**
 *
 * @author Chris 
 */
public class AggregateTargetAbNGenerator {
    
    public TargetAbstractionNetwork createAggregateTargetAbN(
            TargetAbstractionNetwork sourceTargetAbN,
            TargetAbstractionNetworkGenerator generator,
            AggregateAbNGenerator<TargetGroup, AggregateTargetGroup> aggregateGenerator,
            int minGroupSize) {

        if (minGroupSize == 1) {
            return sourceTargetAbN;
        }

        NodeHierarchy<AggregateTargetGroup> reducedTargetHierarchy = aggregateGenerator.createReducedAbN(
                        new AggregateTargetAbNFactory(),
                        sourceTargetAbN.getNodeHierarchy(),
                        minGroupSize);

        return new TargetAbstractionNetwork((NodeHierarchy<TargetGroup>)(NodeHierarchy<?>)reducedTargetHierarchy);
    }
}
