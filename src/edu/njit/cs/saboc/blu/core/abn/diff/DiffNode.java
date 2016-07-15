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
}
