package edu.njit.cs.saboc.blu.core.abn;

import SnomedShared.generic.GenericConceptGroup;
import java.util.HashSet;

/**
 *
 * @author cro3
 */
public class OverlappingConceptResult<CONCEPT_T, GROUP_T extends GenericConceptGroup> {
    
    private final CONCEPT_T concept;
    
    private final HashSet<GROUP_T> overlappingPAreas;
    
    public OverlappingConceptResult(CONCEPT_T concept, HashSet<GROUP_T> overlappingPAreas) {
        this.concept = concept;
        this.overlappingPAreas = overlappingPAreas;
    }
    
    public CONCEPT_T getConcept() {
        return concept;
    }
    
    public HashSet<GROUP_T> getOverlappingGroups() {
        return overlappingPAreas;
    }
}
