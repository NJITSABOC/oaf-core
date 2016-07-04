package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;

/**
 *
 * @author Chris O
 */
public abstract class PartitionedAbNConfiguration extends AbNConfiguration {
    
    public PartitionedAbNConfiguration(PartitionedAbstractionNetwork abstractionNetwork) {
        super(abstractionNetwork);
    }
    
    public abstract int getPartitionedNodeLevel(PartitionedNode node);
    
    public abstract DisjointAbstractionNetwork<?, ?> getDisjointAbstractionNetworkFor(PartitionedNode node);
    
    public void setUIConfiguration(PartitionedAbNUIConfiguration config) {
        super.setUIConfiguration(config);
    }
    
    public void setTextConfiguration(PartitionedAbNTextConfiguration config) {
        super.setTextConfiguration(config);
    }

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
