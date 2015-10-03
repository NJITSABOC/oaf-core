package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;

/**
 *
 * @author Chris
 */
public interface BLUDisjointAbNTextConfiguration<
        DISJOINTABN_T extends DisjointAbstractionNetwork,
        DISJOINTGROUP_T extends DisjointGenericConceptGroup,
        OVERLAPPINGGROUP_T extends GenericConceptGroup, 
        CONCEPT_T> extends BLUAbNTextConfiguration<DISJOINTABN_T, DISJOINTGROUP_T, CONCEPT_T> {
    
     public String getOverlappingGroupTypeName(boolean plural);
     public String getOverlappingGroupName(OVERLAPPINGGROUP_T group);
}
