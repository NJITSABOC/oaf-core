package edu.njit.cs.saboc.blu.core.abn.reduced;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.ConceptGroupHierarchy;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public interface ReducingGroup<CONCEPT_T, GROUP_T extends GenericConceptGroup> {
    public ConceptGroupHierarchy<GROUP_T> getReducedGroupHierarchy();
    
    public HashSet<GROUP_T> getReducedGroups();
    
    public HashSet<CONCEPT_T> getAllGroupsConcepts();
}
