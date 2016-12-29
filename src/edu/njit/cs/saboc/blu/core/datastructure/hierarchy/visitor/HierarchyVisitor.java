package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris O
 */
public abstract class HierarchyVisitor<T> {
    
    private final Hierarchy<T> theHierarchy;
    
    private boolean finished = false;
    
    public HierarchyVisitor(Hierarchy<T> theHierarchy) {
        this.theHierarchy = theHierarchy;
    }
    
    public Hierarchy<T> getHierarchy() {
        return theHierarchy;
    }
    
    public abstract void visit(T node);
    
    public boolean isFinished() {
        return finished;
    }
}
