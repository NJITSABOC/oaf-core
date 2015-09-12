package edu.njit.cs.saboc.blu.core.abn.aggregate;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public interface AggregateableConceptGroup<CONCEPT_T, GROUP_T extends GenericConceptGroup> {
    public GroupHierarchy<GROUP_T> getAggregatedGroupHierarchy();
    
    public HashSet<GROUP_T> getAggregatedGroups();
    
    public HashSet<CONCEPT_T> getAllGroupsConcepts();
}
