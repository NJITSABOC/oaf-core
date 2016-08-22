
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffNode;
import edu.njit.cs.saboc.blu.core.abn.diff.DiffNodeInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;

/**
 *
 * @author Chris O
 */
public class DiffPArea extends PArea implements DiffNodeInstance {

    private final DiffNode diffNode;
    
    public DiffPArea(DiffNode diffNode, PArea changedPArea) {

        super(changedPArea.getHierarchy(), changedPArea.getRelationships());
        
        this.diffNode = diffNode;
    }
    
    public ChangeState getPAreaState() {
        return diffNode.getChangeDetails().getNodeState();
    }
    
    public DiffNode getDiffNode() {
        return diffNode;
    }
}
