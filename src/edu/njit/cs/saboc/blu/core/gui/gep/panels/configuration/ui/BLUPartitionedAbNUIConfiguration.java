package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.listener.BLUAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNNodeInformationPanel;

/**
 *
 * @author Chris O
 */
public abstract class BLUPartitionedAbNUIConfiguration extends BLUAbNUIConfiguration {
    
    protected BLUPartitionedAbNUIConfiguration(BLUAbNListenerConfiguration listenerConfiguration) {
        super(listenerConfiguration);
    }
    
    public abstract boolean hasContainerDetailsPanel();
    public abstract AbNNodeInformationPanel createContainerDetailsPanel();
}
