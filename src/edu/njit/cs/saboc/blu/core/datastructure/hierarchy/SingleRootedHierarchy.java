package edu.njit.cs.saboc.blu.core.datastructure.hierarchy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * @author Chris
 */
public abstract class SingleRootedHierarchy<T> extends MultiRootedHierarchy<T> {
    
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
