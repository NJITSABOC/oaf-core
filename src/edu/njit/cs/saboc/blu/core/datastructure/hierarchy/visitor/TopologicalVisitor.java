package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris O
 */
public abstract class TopologicalVisitor<T> extends HierarchyVisitor<T> {
    
    public TopologicalVisitor(Hierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
}
