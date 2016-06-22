package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text.BLUAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public class BLUConfiguration {
    
    private final BLUAbNUIConfiguration uiConfiguration;
    private final BLUAbNTextConfiguration textConfiguration;
    
    public BLUConfiguration(BLUAbNUIConfiguration uiConfiguration, BLUAbNTextConfiguration textConfiguration) {
        this.uiConfiguration = uiConfiguration;
        this.textConfiguration = textConfiguration;
    }
    
    public BLUAbNUIConfiguration getUIConfiguration() {
        return uiConfiguration;
    }
    
    public BLUAbNTextConfiguration getTextConfiguration() {
        return textConfiguration;
    }
}
