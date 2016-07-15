package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AncestorHierarchyBuilderVisitor<T> extends HierarchyVisitor<T> {
    
    private final Hierarchy<T> ancestorHierarchy;
    
    public AncestorHierarchyBuilderVisitor(
            Hierarchy<T> theHierarchy, 
            Hierarchy<T> ancestorHierarchy) {
        
        super(theHierarchy);
        
        this.ancestorHierarchy = ancestorHierarchy;
    }
    
    public void visit(T node) {

        Set<T> nodeParents = theHierarchy.getParents(node);

        nodeParents.forEach((T parent) -> {
            ancestorHierarchy.addEdge(node, parent);
        });
    }
    
    public Hierarchy<T> getAncestorHierarchy() {
        return ancestorHierarchy;
    }
}
