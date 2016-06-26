package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class TANUIConfiguration extends PartitionedAbNUIConfiguration {
    
    public TANUIConfiguration(TANListenerConfiguration listenerConfig) {
        super(listenerConfig);
    }
    
    public TANListenerConfiguration getListenerConfiguration() {
        return (TANListenerConfiguration)super.getListenerConfiguration();
    }
}