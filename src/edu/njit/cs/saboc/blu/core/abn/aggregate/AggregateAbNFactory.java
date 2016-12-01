package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public interface AggregateAbNFactory<NODE_T extends Node, AGGREGATENODE_T extends Node & AggregateNode<NODE_T>> {
    
    public AGGREGATENODE_T createAggregateNode(
            Hierarchy<NODE_T> aggregatedNodes,
            Hierarchy<Concept> sourceHierarchy
    );
}
