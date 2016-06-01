package edu.njit.cs.saboc.blu.core.abn;

import SnomedShared.generic.GenericConceptGroup;

/**
 *
 * @author Chris O
 */
public class ParentNodeInformation<CONCEPT_T, GROUP_T extends GenericConceptGroup> {

    private CONCEPT_T parentConcept;

    private GROUP_T parentGroup;
    
    public ParentNodeInformation(CONCEPT_T parentConcept, GROUP_T parentGroup) {
        this.parentConcept = parentConcept;
        this.parentGroup = parentGroup;
    }
    
    public CONCEPT_T getParentConcept() {
        return parentConcept;
    }
    
    public GROUP_T getParentGroup() {
        return parentGroup;
    }
}
