package edu.njit.cs.saboc.blu.core.abn.diff.change.childof;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris
 */
public class ChildOfChange {
    private final Node child;
    private final Node parent;
    private final ChangeState state;
    
    public ChildOfChange(Node child, Node parent, ChangeState state) {
        this.child = child;
        this.parent = parent;
        this.state = state;
    }
    
    public Node getChildNode() {
        return child;
    }
    
    public Node getParentNode() {
        return parent;
    }
    
    public ChangeState getChangeState() {
        return state;
    }
}
