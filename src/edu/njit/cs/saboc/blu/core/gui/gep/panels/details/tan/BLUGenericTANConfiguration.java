
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;

/**
 *
 * @author Chris O
 */
public class BLUGenericTANConfiguration<
        T extends BLUGenericTANDataConfiguration,
        V extends BLUGenericTANUIConfiguration,
        U extends BLUGenericTANTextConfiguration> extends BLUPartitionedConfiguration<T, V, U> {
    
    public BLUGenericTANConfiguration() {
        
    }

    public BLUGenericTANConfiguration(T dataConfig, V uiConfig, U textConfig) {
        super(dataConfig, uiConfig, textConfig);
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
