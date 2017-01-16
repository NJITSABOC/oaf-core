package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.DerivedTANFromPartitionedNode;

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
                new DerivedTANFromPartitionedNode(
                        parentAbN.getDerivation(), 
                        tan.getSourceFactory(), 
                        sourceNode));
        
        this.parentAbN = parentAbN;
        this.sourceNode = sourceNode;
    }
    
    @Override
    public DerivedTANFromPartitionedNode getDerivation() {
        return (DerivedTANFromPartitionedNode)super.getDerivation();
    }
    
    public V getParentAbN() {
        return parentAbN;
    }
    
    public T getSourceNode() {
        return sourceNode;
    }
}
