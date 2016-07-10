package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;

/**
 *
 * @author Chris O
 */
public abstract class PartitionedAbNUIConfiguration extends AbNUIConfiguration {
    
    protected PartitionedAbNUIConfiguration(PartitionedAbNListenerConfiguration listenerConfiguration) {
        super(listenerConfiguration);
    }
    
    public PartitionedAbNListenerConfiguration getListenerConfiguration() {
        return (PartitionedAbNListenerConfiguration)super.getListenerConfiguration();
    }
    
    public abstract NodeOptionsPanel getPartitionedNodeOptionsPanel();
    
    public abstract boolean hasContainerDetailsPanel();
    public abstract NodeDashboardPanel createContainerDetailsPanel();
}
