package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text.BLUPartitionedAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public class BLUPartitionedConfiguration extends BLUConfiguration {
    
    public BLUPartitionedConfiguration(
            PartitionedAbstractionNetwork abstractionNetwork,
            BLUAbNUIConfiguration uiConfiguration, 
            BLUPartitionedAbNTextConfiguration textConfiguration) {
        
        super(abstractionNetwork, uiConfiguration, textConfiguration);
    }
    
    @Override
    public BLUPartitionedAbNTextConfiguration getTextConfiguration() {
        return (BLUPartitionedAbNTextConfiguration)super.getTextConfiguration();
    }
}
