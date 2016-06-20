package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;

/**
 *
 * @author Chris O
 */
public interface AggregateAbNFactory<NODE_T extends Node, AGGREGATENODE_T extends Node & AggregateNode<NODE_T>> {
    public AGGREGATENODE_T createAggregateNode(NodeHierarchy<NODE_T> aggregatedNodes);
}
