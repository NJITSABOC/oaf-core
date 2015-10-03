package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.data.BLUAbNDataConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text.BLUDisjointAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public class BLUDisjointConfiguration<
        T extends BLUAbNDataConfiguration, 
        V extends BLUAbNUIConfiguration, 
        U extends BLUDisjointAbNTextConfiguration> extends BLUConfiguration<T, V, U> {
    
    public BLUDisjointConfiguration() {
        
    }
        
    public BLUDisjointConfiguration(T dataConfiguration, V uiConfiguration, U textConfiguration) {
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
