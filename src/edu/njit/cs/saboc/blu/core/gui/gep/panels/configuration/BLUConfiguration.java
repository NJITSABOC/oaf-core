package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.data.BLUAbNDataConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text.BLUAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public class BLUConfiguration<
        T extends BLUAbNDataConfiguration,
        V extends BLUAbNUIConfiguration,
        U extends BLUAbNTextConfiguration> {
    
    private T dataConfiguration;
    private V uiConfiguration;
    private U textConfiguration;
    
    public BLUConfiguration() {
        
    }
    
    public BLUConfiguration(T dataConfiguration, V uiConfiguration, U textConfiguration) {
        this.dataConfiguration = dataConfiguration;
        this.uiConfiguration = uiConfiguration;
        this.textConfiguration = textConfiguration;
    }
    
    public void setDataConfiguration(T dataConfiguration) {
        this.dataConfiguration = dataConfiguration;
    }
    
    public void setUIConfiguration(V uiConfiguration) {
        this.uiConfiguration = uiConfiguration;
    }

    public void setTextConfiguration(U textConfiguration) {
        this.textConfiguration = textConfiguration;
    }
    
    public T getDataConfiguration() {
        return dataConfiguration;
    }
    
    public V getUIConfiguration() {
        return uiConfiguration;
    }
    
    public U getTextConfiguration() {
        return textConfiguration;
    }
}
