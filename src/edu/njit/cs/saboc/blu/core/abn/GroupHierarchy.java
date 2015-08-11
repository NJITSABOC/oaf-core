package edu.njit.cs.saboc.blu.core.abn;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.MultiRootedHierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.Arrays;
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
        super(new HashSet<>(Arrays.asList(root)));
    }
    
    @Override
    public SingleRootedHierarchy<GROUP_T, ? extends SingleRootedHierarchy> getSubhierarchyRootedAt(GROUP_T root) {
        return new SingleRootedGroupHierarchy<GROUP_T>(root);
    }
}
