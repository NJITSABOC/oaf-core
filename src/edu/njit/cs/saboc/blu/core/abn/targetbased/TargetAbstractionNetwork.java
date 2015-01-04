
package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducedAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducedAbNHierarchy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class TargetAbstractionNetwork<GROUP_T extends TargetGroup> extends AbstractionNetwork {
    
    private GROUP_T rootGroup;
    
    public TargetAbstractionNetwork(
            GROUP_T rootGroup,
            ArrayList<? extends TargetContainer> containers,
            HashMap<Integer, GROUP_T> groups,
            HashMap<Integer, HashSet<Integer>> groupHierarchy) {
        
        super(containers, groups, groupHierarchy);
        
        this.rootGroup = rootGroup;
    }
    
    public GROUP_T getRootGroup() {
        return rootGroup;
    }
    
    public TargetAbstractionNetwork<GROUP_T> createReducedTargetAbN (
            TargetAbstractionNetworkGenerator generator, 
            ReducedAbNGenerator<GROUP_T> reducedGroupGenerator, 
            int minGroupSize, int maxGroupSize) {
        
        ReducedAbNHierarchy<GROUP_T> reducedGroupHierarchy = reducedGroupGenerator.createReducedAbN(rootGroup,
                (HashMap<Integer, GROUP_T>)this.groups, groupHierarchy, minGroupSize, maxGroupSize);

        return generator.createTargetAbstractionNetwork(rootGroup, reducedGroupHierarchy.reducedGroups, reducedGroupHierarchy.reducedGroupHierarchy);
    }
}
