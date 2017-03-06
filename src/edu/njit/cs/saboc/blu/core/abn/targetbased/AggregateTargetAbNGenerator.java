package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

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
        
        Hierarchy<AggregateTargetGroup> reducedTargetHierarchy = aggregateGenerator.createAggregateAbN(
                        new AggregateTargetAbNFactory(),
                        sourceTargetAbN.getTargetGroupHierarchy(),
                        sourceTargetAbN.getSourceHierarchy(),
                        minGroupSize);
        
        Hierarchy<TargetGroup> targetHierarchy = (Hierarchy<TargetGroup>)(Hierarchy<?>)reducedTargetHierarchy;
        
        TargetAbstractionNetwork targetAbN = new AggregateTargetAbN(sourceTargetAbN, minGroupSize, targetHierarchy, sourceTargetAbN.getSourceHierarchy());

        targetAbN.setAggregated(true);
        
        return targetAbN;
    }
}
