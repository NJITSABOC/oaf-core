package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeUnmodified;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public class UnmodifiedNode extends DiffNode<NodeUnmodified>{
    
    private final Node unmodifiedNode;
    
    public UnmodifiedNode(Node unmodifiedNode, NodeUnmodified details) {
        super(details);
        
        this.unmodifiedNode = unmodifiedNode;
    }

    public Node getUnmodifiedNode() {
        return unmodifiedNode;
    }
}