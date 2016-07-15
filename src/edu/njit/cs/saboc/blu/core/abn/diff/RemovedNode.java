package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.RemovedNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public class RemovedNode extends DiffNode<RemovedNodeDetails> {
    
    private final Node removedNode;
    
    public RemovedNode(Node removedNode, RemovedNodeDetails details) {
        super(details);
        
        this.removedNode = removedNode;
    }
        
    public Node getRemovedNode() {
        return removedNode;
    }
}