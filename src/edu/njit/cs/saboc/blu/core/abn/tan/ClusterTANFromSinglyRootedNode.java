package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.DerivedTANFromSinglyRootedNode;

/**
 *
 * @author Chris O
 */
public class ClusterTANFromSinglyRootedNode<
        T extends SinglyRootedNode, 
        V extends AbstractionNetwork<T>>
        extends ClusterTribalAbstractionNetwork<Cluster> {
    
    private final V parentAbN;
    private final T sourceNode;
    
    public ClusterTANFromSinglyRootedNode(
            ClusterTribalAbstractionNetwork tan, 
            V parentAbN, 
            T sourceNode) {
        
        super(tan, 
                new DerivedTANFromSinglyRootedNode(
                        parentAbN.getDerivation(), 
                        tan.getSourceFactory(), 
                        sourceNode.getRoot()));
        
        this.parentAbN = parentAbN;
        this.sourceNode = sourceNode;
    }
    
    @Override
    public DerivedTANFromSinglyRootedNode getDerivation() {
        return (DerivedTANFromSinglyRootedNode)super.getDerivation();
    }
    
    public V getParentAbN() {
        return parentAbN;
    }
    
    public T getSourceNode() {
        return sourceNode;
    }
}
