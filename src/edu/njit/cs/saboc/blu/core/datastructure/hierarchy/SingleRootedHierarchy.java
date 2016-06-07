package edu.njit.cs.saboc.blu.core.datastructure.hierarchy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Chris
 */
public class SingleRootedHierarchy<T> extends MultiRootedHierarchy<T> {
    
    public SingleRootedHierarchy(T root) {
        super(new HashSet<T>(Arrays.asList(root)));
    }
    
    public SingleRootedHierarchy(T root, HashMap<T, Set<T>> conceptHierarchy) {
        super(new HashSet<T>(Arrays.asList(root)), conceptHierarchy);
    }
    
    public SingleRootedHierarchy(T root, SingleRootedHierarchy<T> hierarchy) {
        this(root, hierarchy.getAllChildRelationships());
    }
    
    public T getRoot() {
        return getRoots().iterator().next();
    }
}
