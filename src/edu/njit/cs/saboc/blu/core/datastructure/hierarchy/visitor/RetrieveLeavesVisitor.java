package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class RetrieveLeavesVisitor<T> extends HierarchyVisitor<T> {
    private final Set<T> leaves = new HashSet<>();
    
    public RetrieveLeavesVisitor(Hierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
    public void visit(T node) {
        if(theHierarchy.getChildren(node).isEmpty()) {
            leaves.add(node);
        }
    }
    
    public Set<T> getLeaves() {
        return leaves;
    }
}