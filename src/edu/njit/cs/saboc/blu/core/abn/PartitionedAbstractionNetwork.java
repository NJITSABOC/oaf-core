package edu.njit.cs.saboc.blu.core.abn;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Chris O
 */
public abstract class PartitionedAbstractionNetwork<
        CONTAINER_T extends GenericGroupContainer, 
        GROUP_T extends GenericConceptGroup> extends AbstractionNetwork<GROUP_T> {
    
    protected ArrayList<CONTAINER_T> containers;
    
    public PartitionedAbstractionNetwork(ArrayList<CONTAINER_T> containers,
            HashMap<Integer, GROUP_T> groups,
            GroupHierarchy<GROUP_T> groupHierarchy) {
        
        super(groups, groupHierarchy);
        
        this.containers = containers;
    }

    protected int getContainerCount() {
        return containers.size();
    }

    public ArrayList<CONTAINER_T> getContainers() {
        return containers;
    }
}
