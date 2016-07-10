package edu.njit.cs.saboc.blu.core.abn.diff.change;

import edu.njit.cs.saboc.blu.core.abn.diff.change.childof.ChildOfChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class NodeIntroduced extends NodeChange {
    
    public NodeIntroduced(Node node,
            Set<NodeConceptChange> conceptChanges, 
            Set<ChildOfChange> childOfChanges) {
        
        super(NodeChangeState.Introduced, node, conceptChanges, childOfChanges);
    }
}
