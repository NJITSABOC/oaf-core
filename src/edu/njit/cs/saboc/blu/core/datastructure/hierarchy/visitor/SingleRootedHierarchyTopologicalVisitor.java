package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;

/**
 *
 * @author Chris O
 */
public abstract class SingleRootedHierarchyTopologicalVisitor<T> extends SingleRootedHierarchyVisitor<T> {
    
    public SingleRootedHierarchyTopologicalVisitor(SingleRootedHierarchy<T, ? extends SingleRootedHierarchy> theHierarchy) {
        super(theHierarchy);
    }
    
}
