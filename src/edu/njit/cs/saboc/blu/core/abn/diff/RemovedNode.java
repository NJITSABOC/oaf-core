package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeRemoved;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public class RemovedNode extends DiffNode<NodeRemoved> {
    
    private final Node removedNode;
    
    public RemovedNode(Node removedNode, NodeRemoved details) {
        super(details);
        
        this.removedNode = removedNode;
    }
        
    public Node getRemovedNode() {
        return removedNode;
    }
}