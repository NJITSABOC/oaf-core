
package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.reduced.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.reduced.AggregateAbNResult;
import edu.njit.cs.saboc.blu.core.abn.reduced.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import java.util.HashMap;

/**
 *
 * @author Chris O
 */
public abstract class TargetAbstractionNetwork<
        GROUP_T extends TargetGroup, 
        TARGETABN_T extends TargetAbstractionNetwork<GROUP_T, TARGETABN_T>> extends AbstractionNetwork<GROUP_T> 

    implements AggregateableAbstractionNetwork<TARGETABN_T> {
    
    private GROUP_T rootGroup;
    
    protected boolean isReduced = false;
    
    public TargetAbstractionNetwork(
            GROUP_T rootGroup,
            HashMap<Integer, GROUP_T> groups,
            GroupHierarchy<GROUP_T> groupHierarchy) {
        
        super(groups, groupHierarchy);
        
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
}
