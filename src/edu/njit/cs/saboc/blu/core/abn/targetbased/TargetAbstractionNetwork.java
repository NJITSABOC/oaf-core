
package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducedAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducedAbNHierarchy;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducibleAbstractionNetwork;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class TargetAbstractionNetwork<GROUP_T extends TargetGroup, 
        TARGETABN_T extends TargetAbstractionNetwork<GROUP_T, TARGETABN_T>> extends AbstractionNetwork 

    implements ReducibleAbstractionNetwork<TARGETABN_T> {
    
    private GROUP_T rootGroup;
    
    protected boolean isReduced = false;
    
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
    
    public boolean isReduced() {
        return isReduced;
    }
    
    protected void setReduced(boolean reduced) {
        this.isReduced = reduced;
    }
    
    protected TARGETABN_T createReducedTargetAbN (
            TargetAbstractionNetworkGenerator generator, 
            ReducedAbNGenerator<GROUP_T> reducedGroupGenerator, 
            int minGroupSize, int maxGroupSize) {
        
        ReducedAbNHierarchy<GROUP_T> reducedGroupHierarchy = reducedGroupGenerator.createReducedAbN(rootGroup,
                (HashMap<Integer, GROUP_T>)this.groups, groupHierarchy, minGroupSize, maxGroupSize);
        
        TARGETABN_T reducedTargetAbN = (TARGETABN_T)generator.createTargetAbstractionNetwork(
                reducedGroupHierarchy.reducedGroups.get(rootGroup.getId()), 
                reducedGroupHierarchy.reducedGroups, 
                reducedGroupHierarchy.reducedGroupHierarchy);

        reducedTargetAbN.setReduced(true);
        
        return reducedTargetAbN;
    }
}
