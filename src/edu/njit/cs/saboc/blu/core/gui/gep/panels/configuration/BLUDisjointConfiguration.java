package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text.BLUDisjointAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.BLUAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public class BLUDisjointConfiguration extends BLUConfiguration {

    public BLUDisjointConfiguration(
            PartitionedAbstractionNetwork abstractionNetwork,
            BLUAbNUIConfiguration uiConfiguration,
            BLUDisjointAbNTextConfiguration textConfiguration) {

        super(abstractionNetwork, uiConfiguration, textConfiguration);
    }

    @Override
    public BLUDisjointAbNTextConfiguration getTextConfiguration() {
        return (BLUDisjointAbNTextConfiguration) super.getTextConfiguration();
    }
}
