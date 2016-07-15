package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeChangeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Map;

/**
 *
 * @author Chris O
 */
public class AbstractionNetworkDiffResult {
    private final AbstractionNetwork from;
    private final AbstractionNetwork to;
    
    private final Map<Node, NodeChangeDetails> nodeChanges;
    
    public AbstractionNetworkDiffResult(
            AbstractionNetwork from, 
            AbstractionNetwork to, 
            Map<Node, NodeChangeDetails> nodeChanges) {
        
        
        this.from = from;
        this.to = to;
        
        this.nodeChanges = nodeChanges;
    }

    public AbstractionNetwork getFrom() {
        return from;
    }

    public AbstractionNetwork getTo() {
        return to;
    }
    
    public Map<Node, NodeChangeDetails> getNodeChanges() {
        return nodeChanges;
    }
}
