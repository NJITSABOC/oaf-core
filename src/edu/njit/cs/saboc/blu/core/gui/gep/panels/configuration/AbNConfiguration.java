package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 */
public class AbNConfiguration {
    private final AbstractionNetwork abstractionNetwork;
    private final AbNUIConfiguration uiConfiguration;
    private final AbNTextConfiguration textConfiguration;
    
    public AbNConfiguration(
            AbstractionNetwork abstractionNetwork,
            AbNUIConfiguration uiConfiguration, 
            AbNTextConfiguration textConfiguration) {
        
        this.abstractionNetwork = abstractionNetwork;
        
        this.uiConfiguration = uiConfiguration;
        this.textConfiguration = textConfiguration;
    }
    
    public AbstractionNetwork getAbstractionNetwork() {
        return abstractionNetwork;
    }
    
    public AbNUIConfiguration getUIConfiguration() {
        return uiConfiguration;
    }
    
    public AbNTextConfiguration getTextConfiguration() {
        return textConfiguration;
    }
}
