package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import java.util.HashSet;

/**
 *
 * @author cro3
 */
public class OverlappingConceptResult<CONCEPT_T, PAREA_T extends GenericPArea> {
    
    private final CONCEPT_T concept;
    
    private final HashSet<PAREA_T> overlappingPAreas;
    
    public OverlappingConceptResult(CONCEPT_T concept, HashSet<PAREA_T> overlappingPAreas) {
        this.concept = concept;
        this.overlappingPAreas = overlappingPAreas;
    }
    
    public CONCEPT_T getConcept() {
        return concept;
    }
    
    public HashSet<PAREA_T> getOverlappingPAreas() {
        return overlappingPAreas;
    }
}
