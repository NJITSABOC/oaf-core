package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Chris O
 */
public class AbstractionNetworkDiffResult {
    private final AbstractionNetwork from;
    private final AbstractionNetwork to;
    
    private final Set<DiffNode> diffNodes;
    
    private final DiffNodeHierarchy diffHierarchy;
    
    public AbstractionNetworkDiffResult(
            AbstractionNetwork from, 
            AbstractionNetwork to, 
            Set<DiffNode> diffNodes,
            DiffNodeHierarchy diffHierarchy) {
        
        this.from = from;
        this.to = to;
        
        this.diffNodes = diffNodes;
        this.diffHierarchy = diffHierarchy;
    }

    public AbstractionNetwork getFrom() {
        return from;
    }

    public AbstractionNetwork getTo() {
        return to;
    }
    
    public Set<DiffNode> getDiffNodes() {
        return diffNodes;
    }
    
    public Set<DiffNode> getDiffNodesOfType(ChangeState state) {
        
        Set<DiffNode> nodesOfType = diffNodes.stream().filter( (diffNode) -> {
           return diffNode.getChangeDetails().getNodeState().equals(state);
        }).collect(Collectors.toSet());
        
        return nodesOfType;
    }
    
    public DiffNodeHierarchy getDiffNodeHierarchy() {
        return diffHierarchy;
    }
}
