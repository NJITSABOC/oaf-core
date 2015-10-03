package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.data;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.OverlappingConceptResult;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public interface BLUPartitionedAbNDataConfiguration<ABN_T extends AbstractionNetwork, 
        CONTAINER_T extends GenericGroupContainer,
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T> extends BLUAbNDataConfiguration<ABN_T, GROUP_T, CONCEPT_T> {
    
    public ArrayList<GROUP_T> getSortedGroupList(CONTAINER_T container);
    
    public HashSet<GROUP_T> getContainerGroupSet(CONTAINER_T container);
    
    public HashSet<CONCEPT_T> getGroupConceptSet(GROUP_T group);
    
    public HashSet<CONCEPT_T> getContainerOverlappingConcepts(CONTAINER_T container);
    
    public HashSet<OverlappingConceptResult<CONCEPT_T, GROUP_T>> getContainerOverlappingResults(CONTAINER_T container);
        
    public int getContainerLevel(CONTAINER_T container);
}
