package edu.njit.cs.saboc.blu.core.abn.reduced;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import java.util.HashMap;

/**
 *
 * @author Chris O
 */
public class ReducedAbNHierarchy<GROUP_T extends GenericConceptGroup> {
    public final HashMap<Integer, GROUP_T> reducedGroups;
    
    public final GroupHierarchy<GROUP_T> reducedGroupHierarchy;
    
    public ReducedAbNHierarchy(HashMap<Integer, GROUP_T> reducedGroups, GroupHierarchy<GROUP_T> reducedGroupHierarchy) {
        this.reducedGroups = reducedGroups;
        this.reducedGroupHierarchy = reducedGroupHierarchy;
    }
}
