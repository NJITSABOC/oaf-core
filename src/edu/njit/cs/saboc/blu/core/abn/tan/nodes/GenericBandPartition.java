package edu.njit.cs.saboc.blu.core.abn.tan.nodes;

import SnomedShared.generic.GenericContainerPartition;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class GenericBandPartition<CLUSTER_T extends GenericCluster> extends GenericContainerPartition<CLUSTER_T> {
    
    public ArrayList<CLUSTER_T> getClusters() {
        return super.getGroups();
    }
}
