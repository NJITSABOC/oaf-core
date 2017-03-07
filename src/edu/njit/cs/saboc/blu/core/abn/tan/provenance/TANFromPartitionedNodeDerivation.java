package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;

/**
 * Stores the arguments needed to create a TAN from a partitioned node
 * in another abstraction network
 * 
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class TANFromPartitionedNodeDerivation<
        T extends AbNDerivation, 
        V extends PartitionedNode> extends ClusterTANDerivation {
    
    private final T parentAbNDerivation;
    private final V node;
    
    public TANFromPartitionedNodeDerivation(
            T parentAbNDerivation, 
            TANFactory factory,
            V node) {
        
        super(parentAbNDerivation.getSourceOntology(), factory);
        
        this.parentAbNDerivation = parentAbNDerivation;
        this.node = node;
    }
    
    public T getParentAbNDerivation() {
        return parentAbNDerivation;
    }
    
    public V getSourcePartitionedNode() {
        return node;
    }

    @Override
    public String getDescription() {
        return String.format("Derived TAN from %s", node.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        return generator.deriveTANFromMultiRootedHierarchy(
                node.getHierarchy(),
                super.getFactory());
    }
}
