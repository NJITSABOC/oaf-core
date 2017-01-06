package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 */
public interface PartitionedAbNTextConfiguration<T extends SinglyRootedNode, V extends PartitionedNode> extends AbNTextConfiguration<T> {
    public String getBaseAbstractionNetworkTypeName(boolean plural);
    
    public String getContainerTypeName(boolean plural);
    public String getContainerHelpDescription(V container);
    
    public String getDisjointNodeTypeName(boolean plural);
}
