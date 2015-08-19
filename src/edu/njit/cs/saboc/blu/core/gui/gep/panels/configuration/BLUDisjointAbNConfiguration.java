package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;

/**
 *
 * @author Chris O
 */
public interface BLUDisjointAbNConfiguration <
        CONCEPT_T, 
        GROUP_T extends GenericConceptGroup, 
        CONTAINER_T extends GenericGroupContainer, 
        PARENTABN_T extends AbstractionNetwork,
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        DISJOINTGROUP_T extends DisjointGenericConceptGroup<GROUP_T, CONCEPT_T, HIERARCHY_T, DISJOINTGROUP_T>,
        DISJOINTABN_T extends DisjointAbstractionNetwork<PARENTABN_T, GROUP_T, CONCEPT_T, HIERARCHY_T, DISJOINTGROUP_T>> 

            extends BLUPartitionedAbNConfiguration<CONCEPT_T, GROUP_T, CONTAINER_T> {
    
    public String getDisjointGroupTypeName(boolean plural);
    
    public String getDisjointGroupName(DISJOINTGROUP_T group);
    
    public DISJOINTABN_T createDisjointAbN(CONTAINER_T container);
}
