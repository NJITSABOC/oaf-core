package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class SubhierarchyMembersVisitor<T> extends HierarchyVisitor<T> {
    
    private final HashSet<T> members = new HashSet<>();

    public SubhierarchyMembersVisitor(Hierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
    public void visit(T node) {
        members.add(node);
    }
    
    public HashSet<T> getSubhierarchyMembers() {
        return members;
    }
}
