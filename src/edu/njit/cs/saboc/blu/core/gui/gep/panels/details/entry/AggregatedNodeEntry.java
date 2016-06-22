package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregatedNodeEntry {
    
    private final SinglyRootedNode aggregatedNode;
    
    private final Set<AggregateNode<SinglyRootedNode>> aggregatedInto;
    
    public AggregatedNodeEntry(SinglyRootedNode aggregatedNode, Set<AggregateNode<SinglyRootedNode>> aggregatedInto) {
        this.aggregatedNode = aggregatedNode;
        this.aggregatedInto = aggregatedInto;
    }
    
    public SinglyRootedNode getAggregatedNode() {
        return aggregatedNode;
    }
    
    public Set<AggregateNode<SinglyRootedNode>> getAggregatedIntoNodes() {
        return aggregatedInto;
    }
}
