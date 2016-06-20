package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;

/**
 *
 * @author Chris O
 */
public interface AggregateNode<NODE_T extends Node> {
    public NodeHierarchy<NODE_T> getAggregatedHierarchy();
}
