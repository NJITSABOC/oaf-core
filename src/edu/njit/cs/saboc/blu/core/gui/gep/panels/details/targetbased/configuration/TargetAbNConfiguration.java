package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class TargetAbNConfiguration extends AbNConfiguration<TargetGroup> {
    
    public TargetAbNConfiguration(TargetAbstractionNetwork targetAbN) {
        super(targetAbN);
    }
    
    public TargetAbstractionNetwork getTargetAbstractionNetwork() {
        return (TargetAbstractionNetwork)super.getAbstractionNetwork();
    }
    
    public void setUIConfiguration(TargetAbNUIConfiguration uiConfig) {
        super.setUIConfiguration(uiConfig);
    }
    
    public void setTextConfiguration(TargetAbNTextConfiguration textConfig) {
        super.setTextConfiguration(textConfig);
    }
    
    public TargetAbNUIConfiguration getUIConfiguration() {
        return (TargetAbNUIConfiguration)super.getUIConfiguration();
    }

    public TargetAbNTextConfiguration getTextConfiguration() {
        return (TargetAbNTextConfiguration)super.getTextConfiguration();
    }
}
