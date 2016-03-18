package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import java.util.HashSet;

/**
 *
 * @author Den
 */
public class TopRootVisitor<T> extends HierarchyVisitor<T> {
    private HashSet<T> roots = new HashSet<>();
    
    public TopRootVisitor(MultiRootedHierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
    public void visit(T node) {
        if(theHierarchy.getRoots().contains(node)) {
            roots.add(node);
        }
    }
    
    public HashSet<T> getRoots() {
        return roots;
    }
}
