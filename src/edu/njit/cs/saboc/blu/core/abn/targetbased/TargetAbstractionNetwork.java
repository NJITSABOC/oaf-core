
package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class TargetAbstractionNetwork<V extends TargetGroup> extends AbstractionNetwork {
    
    private V rootGroup;
    
    public TargetAbstractionNetwork(
            V rootGroup,
            ArrayList<? extends TargetContainer> containers,
            HashMap<Integer, V> groups,
            HashMap<Integer, HashSet<Integer>> groupHierarchy) {
        
        super(containers, groups, groupHierarchy);
        
        this.rootGroup = rootGroup;
    }
    
    public V getRootGroup() {
        return rootGroup;
    }
}
