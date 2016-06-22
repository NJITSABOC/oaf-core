package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 * 
 * @param <NODE_T> The types of nodes that are aggregated
 */
public interface AggregateNode<NODE_T extends Node> {
    
    public NodeHierarchy<NODE_T> getAggregatedHierarchy();
    
    public Set<NODE_T> getAggregatedNodes();
}
