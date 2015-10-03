package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.data.BLUDisjointableAbNDataConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text.BLUDisjointableAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUPartitionedAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public class BLUDisjointableConfiguration<
        T extends BLUDisjointableAbNDataConfiguration, 
        V extends BLUPartitionedAbNUIConfiguration, 
        U extends BLUDisjointableAbNTextConfiguration> extends BLUPartitionedConfiguration<T, V, U> {
        
    public BLUDisjointableConfiguration() {
        
    }
    
    public BLUDisjointableConfiguration(T dataConfiguration, V uiConfiguration, U textConfiguration) {
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
