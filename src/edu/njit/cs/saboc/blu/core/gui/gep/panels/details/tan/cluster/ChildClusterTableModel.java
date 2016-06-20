package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.abn.tan.nodes.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractChildGroupTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.BLUGenericTANConfiguration;


/**
 *
 * @author Chris O
 */
public class ChildClusterTableModel<CLUSTER_T extends Cluster> extends BLUAbstractChildGroupTableModel<CLUSTER_T> {

    private final BLUGenericTANConfiguration config;
    
    public ChildClusterTableModel(BLUGenericTANConfiguration config) {
        
        super(new String[] {
            "Child Cluster", 
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)), 
            "Tribal Band"
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(CLUSTER_T cluster) {       
        return new Object[] {
            cluster.getRoot().getName(),
            cluster.getConceptCount(),
            config.getTextConfiguration().getGroupsContainerName(cluster).replaceAll(", ", "\n")
        };
    }
}
