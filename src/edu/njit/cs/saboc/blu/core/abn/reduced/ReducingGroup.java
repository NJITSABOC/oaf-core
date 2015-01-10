package edu.njit.cs.saboc.blu.core.abn.reduced;

import SnomedShared.generic.GenericConceptGroup;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public interface ReducingGroup<CONCEPT_T, GROUP_T extends GenericConceptGroup> {
    public HashSet<GROUP_T> getReducedGroups();
    
    public HashSet<CONCEPT_T> getAllGroupsConcepts();
    
    public HashSet<CONCEPT_T> getAllGroupsSourceConcepts();
}
