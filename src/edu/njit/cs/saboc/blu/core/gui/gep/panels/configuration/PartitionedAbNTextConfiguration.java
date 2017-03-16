package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public abstract class PartitionedAbNTextConfiguration<T extends SinglyRootedNode, V extends PartitionedNode> extends AbNTextConfiguration<T> {
    
    public PartitionedAbNTextConfiguration(OntologyEntityNameConfiguration ontologyEntityNameConfiguration) {
        super(ontologyEntityNameConfiguration);
    }
    
    public abstract String getBaseAbstractionNetworkTypeName(boolean plural);
    
    public abstract String getContainerTypeName(boolean plural);
    public abstract String getContainerHelpDescription(V container);
    
    public abstract String getDisjointNodeTypeName(boolean plural);
}
