package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class NodeHierarchy extends MultiRootedHierarchy<Node> {
    
    public NodeHierarchy(HashSet<Node> roots) {
        super(roots);
    }
    
    public NodeHierarchy(Node root) {
        this(new HashSet<>(Arrays.asList(root)));
    }
    
    public NodeHierarchy(HashSet<Node> roots, HashMap<Node, HashSet<Node>> hierarchy) {
        super(roots, hierarchy);
    }
    
    public NodeHierarchy(Node root, HashMap<Node, HashSet<Node>> hierarchy) {
        this(new HashSet<>(Arrays.asList(root)), hierarchy);
    }
    
    public NodeHierarchy(MultiRootedHierarchy<Node> hierarchy) {
        super(hierarchy.getRoots(), hierarchy.getAllChildRelationships());
    }
}