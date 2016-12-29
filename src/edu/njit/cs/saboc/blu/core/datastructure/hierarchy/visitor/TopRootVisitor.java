package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Den
 */
public class TopRootVisitor<T> extends HierarchyVisitor<T> {
    private final Set<T> roots = new HashSet<>();
    
    public TopRootVisitor(Hierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
    public void visit(T node) {
        Hierarchy<T> theHierarchy = super.getHierarchy();
        
        if(theHierarchy.getRoots().contains(node)) {
            roots.add(node);
        }
    }
    
    public Set<T> getRoots() {
        return roots;
    }
}
