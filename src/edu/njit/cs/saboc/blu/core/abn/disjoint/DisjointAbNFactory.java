package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.SingleRootedConceptHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public interface DisjointAbNFactory<PARENTNODE_T extends Node, DISJOINTNODE_T extends DisjointNode<PARENTNODE_T>> {
    
    public DISJOINTNODE_T createDisjointNode(SingleRootedConceptHierarchy hierarchy, Set<PARENTNODE_T> overlaps);
    
}
