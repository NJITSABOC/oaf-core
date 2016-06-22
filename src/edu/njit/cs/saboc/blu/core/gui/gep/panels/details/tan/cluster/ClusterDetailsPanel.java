package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractAbNNodeEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class ClusterDetailsPanel<CONCEPT_T, CLUSTER_T extends Cluster> extends NodeDetailsPanel<CLUSTER_T, CONCEPT_T> {
    
    private final BLUGenericTANConfiguration config;
    
    public ClusterDetailsPanel(
            AbstractNodeOptionsPanel<CLUSTER_T> optionsPanel, 
            NodeEntityList<CLUSTER_T, CONCEPT_T> conceptList,
            BLUGenericTANConfiguration config) {
        
        super(new ClusterSummaryPanel<>(config), 
                optionsPanel,
                conceptList);
        
        this.config = config;
         
        getConceptList().addEntitySelectionListener(config.getUIConfiguration().getListenerConfiguration().getGroupConceptListListener());
    }
    
    @Override
    protected ArrayList<CONCEPT_T> getSortedConceptList(CLUSTER_T cluster) {       
        return config.getDataConfiguration().getSortedConceptList(cluster);
    }
}
