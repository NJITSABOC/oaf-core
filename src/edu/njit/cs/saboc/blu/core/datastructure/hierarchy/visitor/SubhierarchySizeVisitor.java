package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;

/**
 *
 * @author Chris O
 */
public class SubhierarchySizeVisitor<T> extends SingleRootedHierarchyVisitor<T> {
    
    private int count = 0;
    
    public SubhierarchySizeVisitor(SingleRootedHierarchy<T, ? extends SingleRootedHierarchy> theHierarchy) {
        super(theHierarchy);
    }
    
    public void visit(T node) {
        count++;
    }
    
    public int getDescandantCount() {
        return count;
    }
}
