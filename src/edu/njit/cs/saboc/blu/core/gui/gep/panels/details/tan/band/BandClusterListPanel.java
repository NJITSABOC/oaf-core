package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericBand;
import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericCluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractContainerGroupListPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractGroupList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class BandClusterListPanel<
        BAND_T extends GenericBand, 
        CLUSTER_T extends GenericCluster, 
        CONCEPT_T> extends AbstractContainerGroupListPanel<BAND_T, CLUSTER_T, CONCEPT_T> {

    public BandClusterListPanel(
            AbstractGroupList<CLUSTER_T> clusterList, 
            AbstractEntityList<CONCEPT_T> conceptList, 
            BLUGenericTANConfiguration config) {
        
        super(clusterList, conceptList, config);
    }
    
    @Override
    public ArrayList<CONCEPT_T> getSortedConceptList(CLUSTER_T cluster) {
        return configuration.getDataConfiguration().getSortedConceptList(cluster);
    }
}
