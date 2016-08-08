package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeChangeDetails;

/**
 *
 * @author Chris O
 */
public class DiffNode<T extends NodeChangeDetails> {
    private final T changeDetails;
    
    public DiffNode(T changeDetails) {
        this.changeDetails = changeDetails;
    }
    
    public T getChangeDetails() {
        return changeDetails;
    }
    
    public boolean equals(Object o) {
        if(o instanceof DiffNode) {
            DiffNode other = (DiffNode)o;
            
            return other.getChangeDetails().getChangedNode().equals(this.getChangeDetails().getChangedNode()) &&
                    other.getChangeDetails().getNodeState().equals(this.getChangeDetails().getNodeState());
        } 
        
        return false;
    }
    
    public int hashCode() {
        return Integer.hashCode(getChangeDetails().getChangedNode().hashCode() + getChangeDetails().getNodeState().hashCode());
    }
}
