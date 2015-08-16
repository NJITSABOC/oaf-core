package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class OverlappingDetailsEntry<GROUP_T extends GenericConceptGroup, 
        DISJOINTGROUP_T extends DisjointGenericConceptGroup> {
    
    private final GROUP_T overlappingGroup;
    private final HashSet<DISJOINTGROUP_T> disjointGroups;
    
    public OverlappingDetailsEntry(GROUP_T overlappingGroup, HashSet<DISJOINTGROUP_T> commonDisjointGroups) {
        this.overlappingGroup = overlappingGroup;
        this.disjointGroups = commonDisjointGroups;
    }
    
    public GROUP_T getOverlappingGroup() {
        return overlappingGroup;
    }
    
    public HashSet<DISJOINTGROUP_T> getDisjointGroups() {
        return disjointGroups;
    }
}
