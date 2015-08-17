package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public interface BLUPartitionedAbNConfiguration<CONCEPT_T, GROUP_T extends GenericConceptGroup,
        CONTAINER_T extends GenericGroupContainer> extends BLUAbNConfiguration<CONCEPT_T, GROUP_T> {
    
    public String getContainerTypeName(boolean plural);
    
    public String getContainerName(CONTAINER_T container);
    
    public ArrayList<GROUP_T> getSortedGroupList(CONTAINER_T container);
}
