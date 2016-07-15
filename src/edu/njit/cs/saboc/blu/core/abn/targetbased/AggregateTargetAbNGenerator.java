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
        
        Set<TargetGroup> rootGroups = sourceTargetAbN.getTargetGroupHierarchy().getRoots();
        
        rootGroups.forEach( (group) -> {
            roots.add(group.getRoot());
        });
        
        Hierarchy<AggregateTargetGroup> reducedTargetHierarchy = aggregateGenerator.createReducedAbN(
                        new AggregateTargetAbNFactory(),
                        sourceTargetAbN.getTargetGroupHierarchy(),
                        minGroupSize);
        
        Hierarchy<TargetGroup> targetHierarchy = (Hierarchy<TargetGroup>)(Hierarchy<?>)reducedTargetHierarchy;
        
        TargetAbstractionNetwork tan = new AggregateTargetAbN(sourceTargetAbN, minGroupSize, targetHierarchy, sourceTargetAbN.getSourceHierarchy());

        tan.setAggregated(true);
        
        return tan;
    }
}
