package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNResult;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;

/**
 *
 * @author Chris 
 */
public class AggregateTargetAbNGenerator<GROUP_T extends TargetGroup, 
        AGGREGATEGROUP_T extends TargetGroup & AggregateableConceptGroup,
        TARGETABN_T extends TargetAbstractionNetwork<GROUP_T, TARGETABN_T>> {
    
    public TARGETABN_T createReducedTargetAbN (
            TargetAbstractionNetwork sourceTargetAbN,
            TargetAbstractionNetworkGenerator generator, 
            AggregateAbNGenerator<GROUP_T, AGGREGATEGROUP_T> reducedGroupGenerator, 
            int minGroupSize) {
        
        AggregateAbNResult<GROUP_T, AGGREGATEGROUP_T> reducedGroupHierarchy = reducedGroupGenerator.createReducedAbN((GROUP_T)sourceTargetAbN.getRootGroup(),
                sourceTargetAbN.getGroups(), sourceTargetAbN.getGroupHierarchy(), minGroupSize);
        
        TARGETABN_T reducedTargetAbN = (TARGETABN_T)generator.createTargetAbstractionNetwork(
                reducedGroupHierarchy.reducedGroups.get(sourceTargetAbN.getRootGroup().getId()), 
                reducedGroupHierarchy.reducedGroups, 
                reducedGroupHierarchy.reducedGroupHierarchy);

        reducedTargetAbN.setReduced(true);
        
        return reducedTargetAbN;
    }
    
}
