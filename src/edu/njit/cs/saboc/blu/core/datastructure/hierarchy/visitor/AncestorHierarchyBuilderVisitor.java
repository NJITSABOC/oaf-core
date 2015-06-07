package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class AncestorHierarchyBuilderVisitor<T, HIERARCHY_T extends SingleRootedHierarchy<T, HIERARCHY_T>> extends SingleRootedHierarchyVisitor<T> {
    
    private HIERARCHY_T ancestorHierarchy;
    
    public AncestorHierarchyBuilderVisitor(SingleRootedHierarchy<T, HIERARCHY_T> theHierarchy, 
            HIERARCHY_T ancestorHierarchy) {
        
        super(theHierarchy);
        
        this.ancestorHierarchy = ancestorHierarchy;
    }
    
    public void visit(T node) {

        HashSet<T> nodeParents = theHierarchy.getParents(node);

        nodeParents.forEach((T parent) -> {
            ancestorHierarchy.addIsA(node, parent);
        });
    }
    
    public HIERARCHY_T getAncestorHierarchy() {
        return ancestorHierarchy;
    }
}
