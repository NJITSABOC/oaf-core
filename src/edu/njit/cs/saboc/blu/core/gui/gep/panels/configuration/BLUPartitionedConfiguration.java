package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.data.BLUPartitionedAbNDataConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text.BLUPartitionedAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUPartitionedAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public class BLUPartitionedConfiguration<
        T extends BLUPartitionedAbNDataConfiguration, 
        V extends BLUPartitionedAbNUIConfiguration, 
        U extends BLUPartitionedAbNTextConfiguration> extends BLUConfiguration<T, V, U> {

    public BLUPartitionedConfiguration() {
        
    }
    
    public BLUPartitionedConfiguration(T dataConfiguration, V uiConfiguration, U textConfiguration) {
        super(dataConfiguration, uiConfiguration, textConfiguration);
    }

    public T getDataConfiguration() {
        return super.getDataConfiguration();
    }

    public V getUIConfiguration() {
        return super.getUIConfiguration();
    }

    public U getTextConfiguration() {
        return super.getTextConfiguration();
    }
}
