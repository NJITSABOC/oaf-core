package edu.njit.cs.saboc.blu.core.abn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GroupHierarchy<GROUP_T extends GenericConceptGroup> extends MultiRootedHierarchy<GROUP_T> {
    
    public GroupHierarchy(HashSet<GROUP_T> roots) {
        super(roots);
    }
    
    public GroupHierarchy(GROUP_T root) {
        this(new HashSet<>(Arrays.asList(root)));
    }
    
    public GroupHierarchy(HashSet<GROUP_T> roots, HashMap<GROUP_T, HashSet<GROUP_T>> hierarchy) {
        super(roots, hierarchy);
    }
    
    public GroupHierarchy(GROUP_T root, HashMap<GROUP_T, HashSet<GROUP_T>> hierarchy) {
        this(new HashSet<>(Arrays.asList(root)), hierarchy);
    }
    
    @Override
    public SingleRootedHierarchy<GROUP_T, ? extends SingleRootedHierarchy> getSubhierarchyRootedAt(GROUP_T root) {
        return new SingleRootedGroupHierarchy<GROUP_T>(root, this.children);
    }
}
