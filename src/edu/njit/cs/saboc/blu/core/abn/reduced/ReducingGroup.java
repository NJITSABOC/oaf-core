package edu.njit.cs.saboc.blu.core.abn.reduced;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public interface ReducingGroup<CONCEPT_T, GROUP_T extends GenericConceptGroup> {
    public GroupHierarchy<GROUP_T> getReducedGroupHierarchy();
    
    public HashSet<GROUP_T> getReducedGroups();
    
    public HashSet<CONCEPT_T> getAllGroupsConcepts();
}
