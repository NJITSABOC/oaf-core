package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.text;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;

/**
 *
 * @author Chris O
 */
public interface BLUPartitionedAbNTextConfiguration extends BLUAbNTextConfiguration {
    public String getContainerTypeName(boolean plural);
    public String getContainerHelpDescription(PartitionedNode container);
    
    public String getDisjointGroupTypeName(boolean plural);
}
