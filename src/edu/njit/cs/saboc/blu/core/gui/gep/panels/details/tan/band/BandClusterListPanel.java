package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.PartitionedNodeSubNodeList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class BandClusterListPanel<
        BAND_T extends Band, 
        CLUSTER_T extends Cluster, 
        CONCEPT_T> extends PartitionedNodeSubNodeList<BAND_T, CLUSTER_T, CONCEPT_T> {

    public BandClusterListPanel(
            NodeList<CLUSTER_T> clusterList, 
            AbstractEntityList<CONCEPT_T> conceptList, 
            BLUGenericTANConfiguration config) {
        
        super(clusterList, conceptList, config);
    }
    
    @Override
    public ArrayList<CONCEPT_T> getSortedConceptList(CLUSTER_T cluster) {
        return configuration.getDataConfiguration().getSortedConceptList(cluster);
    }
}
