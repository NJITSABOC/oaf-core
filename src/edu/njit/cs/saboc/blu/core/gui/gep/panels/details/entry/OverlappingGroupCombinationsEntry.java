package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class OverlappingGroupCombinationsEntry<GROUP_T extends GenericConceptGroup, DISJOINTGROUP_T extends DisjointGenericConceptGroup, CONCEPT_T> {

    private final HashSet<DISJOINTGROUP_T> disjointGroups;
    
    private final GROUP_T overlappingSource;
    
    public OverlappingGroupCombinationsEntry(GROUP_T overlappingSource, HashSet<DISJOINTGROUP_T> disjointGroups) {
        this.overlappingSource = overlappingSource;
        this.disjointGroups = disjointGroups;
    }
    
    public HashSet<GROUP_T> getOtherOverlappingGroups() {
        HashSet<GROUP_T> groups = new HashSet<>(disjointGroups.iterator().next().getOverlaps());
        groups.remove(overlappingSource);
        
        return groups;
    }
    
    public GROUP_T getOverlappingSource() {
        return overlappingSource;
    }
    
    public HashSet<DISJOINTGROUP_T> getDisjointGroups() {
        return disjointGroups;
    }
    
    public HashSet<CONCEPT_T> getOverlappingConcepts() {
        
        HashSet<CONCEPT_T> overlappingConcepts = new HashSet<>();
        
        disjointGroups.forEach( (DISJOINTGROUP_T group) -> {
            overlappingConcepts.addAll(group.getConceptHierarchy().getNodesInHierarchy());
        });

        return overlappingConcepts;
    }
}
