package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;

/**
 *
 * @author Chris O
 */
public class SubhierarchySizeVisitor<T> extends HierarchyVisitor<T> {
    
    private int count = 0;
    
    public SubhierarchySizeVisitor(MultiRootedHierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
    public void visit(T node) {
        count++;
    }
    
    public int getDescandantCount() {
        return count;
    }
}
