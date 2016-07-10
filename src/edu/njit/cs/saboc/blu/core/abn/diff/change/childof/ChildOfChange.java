package edu.njit.cs.saboc.blu.core.abn.diff.change.childof;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris
 */
public class ChildOfChange {
    private final Node child;
    private final Node parent;
    
    public ChildOfChange(Node child, Node parent) {
        this.child = child;
        this.parent = parent;
    }
    
    public Node getChildNode() {
        return child;
    }
    
    public Node getParentNode() {
        return parent;
    }
}
