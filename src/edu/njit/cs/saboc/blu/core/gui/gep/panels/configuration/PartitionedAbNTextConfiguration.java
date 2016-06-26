package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;

/**
 *
 * @author Chris O
 */
public interface PartitionedAbNTextConfiguration extends AbNTextConfiguration {
    public String getContainerTypeName(boolean plural);
    public String getContainerHelpDescription(PartitionedNode container);
    
    public String getDisjointNodeTypeName(boolean plural);
}
