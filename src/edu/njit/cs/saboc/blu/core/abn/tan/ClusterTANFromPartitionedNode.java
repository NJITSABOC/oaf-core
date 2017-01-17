package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.TANFromPartitionedNodeDerivation;

/**
 *
 * @author Chris O
 */
public class ClusterTANFromPartitionedNode<
        T extends PartitionedNode, 
        V extends PartitionedAbstractionNetwork>

        extends ClusterTribalAbstractionNetwork<Cluster> {
    
    private final V parentAbN;
    private final T sourceNode;
    
    public ClusterTANFromPartitionedNode(
            ClusterTribalAbstractionNetwork tan, 
            V parentAbN, 
            T sourceNode) {
        
        super(tan, 
                new TANFromPartitionedNodeDerivation(
                        parentAbN.getDerivation(), 
                        tan.getSourceFactory(), 
                        sourceNode));
        
        this.parentAbN = parentAbN;
        this.sourceNode = sourceNode;
    }
    
    public ClusterTANFromPartitionedNode(ClusterTANFromPartitionedNode<T, V> tan) {
        this(tan, tan.getParentAbN(), tan.getSourceNode());
    }
    
    @Override
    public TANFromPartitionedNodeDerivation getDerivation() {
        return (TANFromPartitionedNodeDerivation)super.getDerivation();
    }
    
    public V getParentAbN() {
        return parentAbN;
    }
    
    public T getSourceNode() {
        return sourceNode;
    }
}
