package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;

/**
 *
 * @author Chris O
 */
public abstract class PartitionedAbNConfiguration extends AbNConfiguration {
    
    public PartitionedAbNConfiguration(
            PartitionedAbstractionNetwork abstractionNetwork,
            PartitionedAbNUIConfiguration uiConfiguration, 
            PartitionedAbNTextConfiguration textConfiguration) {
        
        super(abstractionNetwork, uiConfiguration, textConfiguration);
    }
    
    public abstract int getPartitionedNodeLevel(PartitionedNode node);

    public PartitionedAbstractionNetwork getAbstractionNetwork() {
        return (PartitionedAbstractionNetwork)super.getAbstractionNetwork();
    }
    
    @Override
    public PartitionedAbNUIConfiguration getUIConfiguration() {
        return (PartitionedAbNUIConfiguration)super.getUIConfiguration();
    }
    
    @Override
    public PartitionedAbNTextConfiguration getTextConfiguration() {
        return (PartitionedAbNTextConfiguration)super.getTextConfiguration();
    }
}
