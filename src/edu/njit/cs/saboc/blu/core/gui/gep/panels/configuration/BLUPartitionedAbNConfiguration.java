package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.OverlappingConceptResult;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public interface BLUPartitionedAbNConfiguration<CONCEPT_T, GROUP_T extends GenericConceptGroup,
        CONTAINER_T extends GenericGroupContainer> extends BLUAbNConfiguration<CONCEPT_T, GROUP_T> {
    
    public String getContainerTypeName(boolean plural);
    
    public String getContainerName(CONTAINER_T container);
    
    public String getGroupsContainerName(GROUP_T group);
    
    public ArrayList<GROUP_T> getSortedGroupList(CONTAINER_T container);
    
    public HashSet<GROUP_T> getContainerGroupSet(CONTAINER_T container);
    
    public HashSet<CONCEPT_T> getGroupConceptSet(GROUP_T group);
    
    public HashSet<CONCEPT_T> getContainerOverlappingConcepts(CONTAINER_T container);
    
    public HashSet<OverlappingConceptResult<CONCEPT_T, GROUP_T>> getContainerOverlappingResults(CONTAINER_T container);
        
    public int getContainerLevel(CONTAINER_T container);
}
