package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry;

import SnomedShared.generic.GenericConceptGroup;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class OverlappingGroupEntry<GROUP_T extends GenericConceptGroup, CONCEPT_T> {
    
    private final GROUP_T overlappingGroup;
    private final HashSet<CONCEPT_T> overlappingConcepts;
    
    public OverlappingGroupEntry(GROUP_T overlappingGroup, HashSet<CONCEPT_T> overlappingConcepts) {
        this.overlappingGroup = overlappingGroup;
        this.overlappingConcepts = overlappingConcepts;
    }
    
    public GROUP_T getOverlappingGroup() {
        return overlappingGroup;
    }
    
    public HashSet<CONCEPT_T> getOverlappingConcepts() {
        return overlappingConcepts;
    }
}
