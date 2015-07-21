package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.GenericParentGroupInfo;

/**
 *
 * @author Chris O
 */
public class GenericParentPAreaInfo<CONCEPT_T, PAREA_T extends GenericPArea> extends GenericParentGroupInfo<CONCEPT_T, PAREA_T> {

    public GenericParentPAreaInfo(CONCEPT_T parentConcept, PAREA_T parentPArea) {
        super(parentConcept, parentPArea);
    }

    public PAREA_T getParentPArea() {
        return super.getParentGroup();
    }
}
