package edu.njit.cs.saboc.blu.core.datastructure.hierarchy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.AllPathsToNodeVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.AncestorDepthVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.AncestorHierarchyBuilderVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.TopologicalVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.HierarchyVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.SubhierarchyMembersVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.SubhierarchySizeVisitor;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.result.AncestorDepthResult;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

/**
 * 
 * @author Chris
 */
public class SingleRootedHierarchy<T> extends MultiRootedHierarchy<T> {
    
    public SingleRootedHierarchy(T root) {
        super(new HashSet<T>(Arrays.asList(root)));
    }
    
    public SingleRootedHierarchy(T root, HashMap<T, HashSet<T>> conceptHierarchy) {
        super(new HashSet<T>(Arrays.asList(root)), conceptHierarchy);
    }
    
    public SingleRootedHierarchy(T root, SingleRootedHierarchy<T> hierarchy) {
        this(root, hierarchy.children);
    }
    
    public T getRoot() {
        return getRoots().iterator().next();
    }
}
