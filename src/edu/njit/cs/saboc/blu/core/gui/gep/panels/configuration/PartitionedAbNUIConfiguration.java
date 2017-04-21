package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.CompactNodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public abstract class PartitionedAbNUIConfiguration<T extends SinglyRootedNode, V extends PartitionedNode> extends AbNUIConfiguration<T> {
    
    protected PartitionedAbNUIConfiguration(
            AbNDisplayManager displayManager, 
            PartitionedAbNListenerConfiguration listenerConfiguration) {
        
        super(displayManager, listenerConfiguration);
    }
    
    @Override
    public PartitionedAbNListenerConfiguration getListenerConfiguration() {
        return (PartitionedAbNListenerConfiguration)super.getListenerConfiguration();
    }
    
    public abstract NodeOptionsPanel<V> getPartitionedNodeOptionsPanel();
    
    public abstract boolean hasPartitionedNodeDetailsPanel();
    public abstract NodeDashboardPanel<V> createPartitionedNodeDetailsPanel();
    public abstract CompactNodeDashboardPanel<V> createCompactPartitionedNodeDetailsPanel();
}
