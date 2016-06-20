
package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;

/**
 *
 * @author Chris O
 */
public class TargetAbstractionNetwork extends AbstractionNetwork<TargetGroup> {
    
    public TargetAbstractionNetwork(NodeHierarchy<TargetGroup> groupHierarchy) {
        super(groupHierarchy);
    }
    
    public NodeHierarchy<TargetGroup> getTargetGroupHierarchy() {
        return super.getNodeHierarchy();
    }
}
