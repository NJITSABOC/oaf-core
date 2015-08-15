package edu.njit.cs.saboc.blu.core.abn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class SingleRootedGroupHierarchy<T extends GenericConceptGroup> extends SingleRootedHierarchy<T, SingleRootedGroupHierarchy<T>> {
    public SingleRootedGroupHierarchy(T root) {
        super(root);
    }
    
    public SingleRootedGroupHierarchy(T root, HashMap<T, HashSet<T>> groupHierarchy) {
        super(root, groupHierarchy);
    }
    
    public SingleRootedGroupHierarchy<T> getSubhierarchyRootedAt(T root) {
        return new SingleRootedGroupHierarchy<T>(root, this.children);
    }
        
    public GroupHierarchy<T> asGroupHierarchy() {
        return new GroupHierarchy<T>(this.getRoot(), this.children);
    }
    
    protected SingleRootedGroupHierarchy<T> createHierarchy(T root) {
        return new SingleRootedGroupHierarchy<T>(root); 
    }
}
