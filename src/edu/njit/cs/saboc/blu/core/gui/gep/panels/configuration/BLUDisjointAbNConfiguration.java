package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;

/**
 *
 * @author Chris O
 */
public interface BLUDisjointAbNConfiguration<CONCEPT_T, 
        GROUP_T extends GenericConceptGroup, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        DISJOINTGROUP_T extends DisjointGenericConceptGroup<GROUP_T, CONCEPT_T, HIERARCHY_T, DISJOINTGROUP_T>>

            extends BLUAbNConfiguration<CONCEPT_T, DISJOINTGROUP_T> {
    
    public String getOverlappingGroupTypeName(boolean plural);
    
    public String getOverlappingGroupName(GROUP_T group);
}
