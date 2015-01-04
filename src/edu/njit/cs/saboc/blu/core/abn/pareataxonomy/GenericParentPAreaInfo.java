package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

/**
 *
 * @author Chris O
 */
public class GenericParentPAreaInfo<CONCEPT_T, PAREA_T extends GenericPArea> {
    
    private CONCEPT_T parentConcept;
    
    private PAREA_T parentPArea;
    
    public GenericParentPAreaInfo(CONCEPT_T parentConcept, PAREA_T parentPArea) {
        this.parentConcept = parentConcept;
        this.parentPArea = parentPArea;
    }
    
    public CONCEPT_T getParentConcept() {
        return parentConcept;
    }
    
    public PAREA_T getParentPArea() {
        return parentPArea;
    }
}
