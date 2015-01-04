package edu.njit.cs.saboc.blu.core.abn.reduced;

import SnomedShared.generic.GenericConceptGroup;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class ReducedAbNHierarchy<GROUP_T extends GenericConceptGroup> {
    public final HashMap<Integer, GROUP_T> reducedGroups;
    
    public final HashMap<Integer, HashSet<Integer>> reducedGroupHierarchy;
    
    public ReducedAbNHierarchy(HashMap<Integer, GROUP_T> reducedGroups, HashMap<Integer, HashSet<Integer>> reducedGroupHierarchy) {
        this.reducedGroups = reducedGroups;
        this.reducedGroupHierarchy = reducedGroupHierarchy;
    }
}
