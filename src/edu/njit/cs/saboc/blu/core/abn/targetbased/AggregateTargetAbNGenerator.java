package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

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
        
        Set<Concept> roots = new HashSet<>();
        
        sourceTargetAbN.getTargetGroupHierarchy().getRoots().forEach( (group) -> {
            roots.add(group.getRoot());
        });
        
        Hierarchy<AggregateTargetGroup> reducedTargetHierarchy = aggregateGenerator.createReducedAbN(
                        new AggregateTargetAbNFactory(),
                        sourceTargetAbN.getNodeHierarchy(),
                        minGroupSize);
        
        Hierarchy<TargetGroup> targetHierarchy = (Hierarchy<TargetGroup>)(Hierarchy<?>)reducedTargetHierarchy;

        return new TargetAbstractionNetwork(targetHierarchy, sourceTargetAbN.getSourceHierarchy());
    }
}
