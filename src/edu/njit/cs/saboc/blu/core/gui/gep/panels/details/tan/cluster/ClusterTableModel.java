package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.NodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;

/**
 *
 * @author Chris O
 */
public class ClusterTableModel<CLUSTER_T extends Cluster> extends NodeTableModel<CLUSTER_T> {
    
    public ClusterTableModel(BLUGenericTANConfiguration config) {
        super(config);
    }
}
