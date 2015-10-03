package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;

/**
 *
 * @author Chris O
 */
public interface BLUDisjointableAbNTextConfiguration<ABN_T extends AbstractionNetwork, 
        DISJOINTABN_T extends DisjointAbstractionNetwork,
        CONTAINER_T extends GenericGroupContainer,
        GROUP_T extends GenericConceptGroup, 
        DISJOINTGROUP_T extends DisjointGenericConceptGroup,
        CONCEPT_T> extends BLUPartitionedAbNTextConfiguration<ABN_T, CONTAINER_T, GROUP_T, CONCEPT_T> {
    
    public String getDisjointGroupTypeName(boolean plural);
    
    public String getDisjointGroupName(DISJOINTGROUP_T group);
}
