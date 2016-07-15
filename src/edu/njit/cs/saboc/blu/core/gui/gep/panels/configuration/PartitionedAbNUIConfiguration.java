package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;

/**
 *
 * @author Chris O
 */
public abstract class PartitionedAbNUIConfiguration<T extends SinglyRootedNode, V extends PartitionedNode> extends AbNUIConfiguration<T> {
    
    protected PartitionedAbNUIConfiguration(PartitionedAbNListenerConfiguration listenerConfiguration) {
        super(listenerConfiguration);
    }
    
    public PartitionedAbNListenerConfiguration getListenerConfiguration() {
        return (PartitionedAbNListenerConfiguration)super.getListenerConfiguration();
    }
    
    public abstract NodeOptionsPanel<V> getPartitionedNodeOptionsPanel();
    
    public abstract boolean hasContainerDetailsPanel();
    public abstract NodeDashboardPanel<V> createContainerDetailsPanel();
}
