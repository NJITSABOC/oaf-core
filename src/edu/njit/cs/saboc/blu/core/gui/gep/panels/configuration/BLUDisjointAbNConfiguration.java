package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;

/**
 *
 * @author Chris O
 */
public interface BLUDisjointAbNConfiguration <CONCEPT_T, 
        GROUP_T extends GenericConceptGroup, 
        CONTAINER_T extends GenericGroupContainer, 
        DISJOINTGROUP_T extends DisjointGenericConceptGroup> extends BLUPartitionedAbNConfiguration<CONCEPT_T, GROUP_T, CONTAINER_T> {
    
    
    public String getDisjointGroupTypeName(boolean plural);
    
    public String getDisjointGroupName(DISJOINTGROUP_T group);
}
