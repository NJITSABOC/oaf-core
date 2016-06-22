package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text.BLUAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public class BLUConfiguration {
    private final AbstractionNetwork abstractionNetwork;
    private final BLUAbNUIConfiguration uiConfiguration;
    private final BLUAbNTextConfiguration textConfiguration;
    
    public BLUConfiguration(
            AbstractionNetwork abstractionNetwork,
            BLUAbNUIConfiguration uiConfiguration, 
            BLUAbNTextConfiguration textConfiguration) {
        
        this.abstractionNetwork = abstractionNetwork;
        
        this.uiConfiguration = uiConfiguration;
        this.textConfiguration = textConfiguration;
    }
    
    public AbstractionNetwork getAbstractionNetwork() {
        return abstractionNetwork;
    }
    
    public BLUAbNUIConfiguration getUIConfiguration() {
        return uiConfiguration;
    }
    
    public BLUAbNTextConfiguration getTextConfiguration() {
        return textConfiguration;
    }
}
