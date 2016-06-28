package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 */
public class AbNConfiguration {
    private final AbstractionNetwork abstractionNetwork;
    private AbNUIConfiguration uiConfiguration;
    private AbNTextConfiguration textConfiguration;
    
    public AbNConfiguration(AbstractionNetwork abstractionNetwork) {
        this.abstractionNetwork = abstractionNetwork;
    }
    
    public void setUIConfiguration(AbNUIConfiguration uiConfiguration) {
        this.uiConfiguration = uiConfiguration;
    }

    public void setTextConfiguration(AbNTextConfiguration textConfiguration) {
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
