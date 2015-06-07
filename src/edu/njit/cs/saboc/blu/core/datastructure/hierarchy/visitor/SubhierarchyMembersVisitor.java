package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class SubhierarchyMembersVisitor<T> extends SingleRootedHierarchyVisitor<T> {
    
    private HashSet<T> members = new HashSet<>();

    public SubhierarchyMembersVisitor(SingleRootedHierarchy<T, ? extends SingleRootedHierarchy> theHierarchy) {
        super(theHierarchy);
    }
    
    public void visit(T node) {
        members.add(node);
    }
    
    public HashSet<T> getSubhierarchyMembers() {
        return members;
    }
}
