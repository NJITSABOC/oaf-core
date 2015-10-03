package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 */
public interface BLUPartitionedAbNTextConfiguration<
        ABN_T extends AbstractionNetwork, 
        CONTAINER_T extends GenericGroupContainer,
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T> extends BLUAbNTextConfiguration<ABN_T, GROUP_T, CONCEPT_T> {
    
    public String getContainerTypeName(boolean plural);
    
    public String getContainerHelpDescription(CONTAINER_T container);
    
    public String getContainerName(CONTAINER_T container);
    
    public String getGroupsContainerName(GROUP_T group);
}
