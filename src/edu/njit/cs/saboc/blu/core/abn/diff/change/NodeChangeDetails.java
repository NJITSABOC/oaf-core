package edu.njit.cs.saboc.blu.core.abn.diff.change;

import edu.njit.cs.saboc.blu.core.abn.diff.change.childof.ChildOfChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public abstract class NodeChangeDetails extends AbNChange {
    
    private final Node changedNode;
    
    private final NodeChangeState changeState;
    
    private final Set<NodeConceptChange> conceptChanges;
    
    private final Set<ChildOfChange> childOfChanges;
    
    protected NodeChangeDetails(NodeChangeState changeState, Node changedNode, 
            Set<NodeConceptChange> conceptChanges,
            Set<ChildOfChange> childOfChanges) {
        
        this.changeState = changeState;
        this.changedNode = changedNode;
        this.conceptChanges = conceptChanges;
        this.childOfChanges = childOfChanges;
    }
    
    public NodeChangeState getNodeState() {
        return changeState;
    }
    
    public Node getChangedNode() {
        return changedNode;
    }
    
    public Set<NodeConceptChange> getConceptChanges() {
        return conceptChanges;
    }
    
    public Set<ChildOfChange> getChildOfChanges() {
        return childOfChanges;
    }
}