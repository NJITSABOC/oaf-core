package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class DerivedTANFromPartitionedNode<
        T extends AbNDerivation, 
        V extends PartitionedNode> extends DerivedClusterTAN {
    
    private final T parentAbNDerivation;
    private final V node;
    
    public DerivedTANFromPartitionedNode(
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
