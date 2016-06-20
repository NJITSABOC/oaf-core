package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AncestorHierarchyBuilderVisitor<T> extends HierarchyVisitor<T> {
    
    private final MultiRootedHierarchy<T> ancestorHierarchy;
    
    public AncestorHierarchyBuilderVisitor(
            MultiRootedHierarchy<T> theHierarchy, 
            MultiRootedHierarchy<T> ancestorHierarchy) {
        
        super(theHierarchy);
        
        this.ancestorHierarchy = ancestorHierarchy;
    }
    
    public void visit(T node) {

        Set<T> nodeParents = theHierarchy.getParents(node);

        nodeParents.forEach((T parent) -> {
            ancestorHierarchy.addIsA(node, parent);
        });
    }
    
    public MultiRootedHierarchy<T> getAncestorHierarchy() {
        return ancestorHierarchy;
    }
}
