package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;

/**
 *
 * @author Chris O
 */
public abstract class SingleRootedHierarchyVisitor<T> {
    
    protected SingleRootedHierarchy<T, ? extends SingleRootedHierarchy> theHierarchy;
    
    private boolean finished = false;
    
    public SingleRootedHierarchyVisitor(SingleRootedHierarchy<T, ? extends SingleRootedHierarchy> theHierarchy) {
        this.theHierarchy = theHierarchy;
    }
    
    public abstract void visit(T node);
    
    public boolean isFinished() {
        return finished;
    }
}
