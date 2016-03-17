package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;

/**
 *
 * @author Chris O
 */
public abstract class TopologicalVisitor<T> extends HierarchyVisitor<T> {
    
    public TopologicalVisitor(MultiRootedHierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
}
