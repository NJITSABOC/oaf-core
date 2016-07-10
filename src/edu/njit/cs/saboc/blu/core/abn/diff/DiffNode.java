package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeChange;

/**
 *
 * @author Chris O
 */
public class DiffNode<T extends NodeChange> {
    private final T changeDetails;
    
    public DiffNode(T changeDetails) {
        this.changeDetails = changeDetails;
    }
    
    public T getChangeDetails() {
        return changeDetails;
    }
}
