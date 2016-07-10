package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeModified;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public class ModifiedNode extends DiffNode<NodeModified> {

    private final Node from;
    private final Node to;
    
    public ModifiedNode(Node from, Node to, NodeModified details) {
        super(details);
        
        this.from = from;
        this.to = to;
    }
    
    public Node getFromNode() {
        return from;
    }
    
    public Node getToNode() {
        return to;
    }
}