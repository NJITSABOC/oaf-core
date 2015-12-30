package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.abn.tan.nodes.GenericCluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractGroupTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;

/**
 *
 * @author Chris O
 */
public class ClusterTableModel<CLUSTER_T extends GenericCluster> extends BLUAbstractGroupTableModel<CLUSTER_T> {
    
    public ClusterTableModel(BLUGenericTANConfiguration config) {
        super(config);
    }
}
