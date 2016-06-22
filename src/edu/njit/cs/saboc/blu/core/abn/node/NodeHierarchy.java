package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Chris O
 * 
 * @param <NODE_T>
 */
public class NodeHierarchy<NODE_T extends Node> extends Hierarchy<NODE_T> {
    
    public NodeHierarchy(Set<NODE_T> roots) {
        super(roots);
    }
    
    public NodeHierarchy(NODE_T root) {
        this(Collections.singleton(root));
    }
    
    public NodeHierarchy(Set<NODE_T> roots, HashMap<NODE_T, Set<NODE_T>> hierarchy) {
        super(roots, hierarchy);
    }
    
    public NodeHierarchy(NODE_T root, HashMap<NODE_T, Set<NODE_T>> hierarchy) {
        this(Collections.singleton(root), hierarchy);
    }
    
    public NodeHierarchy(Hierarchy<NODE_T> hierarchy) {
        super(hierarchy.getRoots(), hierarchy.getAllChildRelationships());
    }
    
    @Override
    public NodeHierarchy<NODE_T> getAncestorHierarchy(NODE_T node) {
        return asNodeHierarchy(super.getAncestorHierarchy(node));
    }
    
    @Override
    public NodeHierarchy<NODE_T> getSubhierarchyRootedAt(NODE_T node) {
        return asNodeHierarchy(super.getSubhierarchyRootedAt(node));
    }
    
    private NodeHierarchy<NODE_T> asNodeHierarchy(Hierarchy<NODE_T> hierarchy) {
        return new NodeHierarchy<>(hierarchy.getRoots(), hierarchy.getAllChildRelationships());
    }
}