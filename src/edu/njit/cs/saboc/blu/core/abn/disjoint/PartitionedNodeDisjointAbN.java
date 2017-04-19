package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.PartitionedNodeDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 * A disjoint abstraction network created from a partitioned node (e.g., an area)
 * 
 * @author Chris O
 * 
 * @param <T>
 * @param <PARENTABN_T>
 * @param <PARTITIONNODE_T>
 * @param <PARENTNODE_T>
 */
public class PartitionedNodeDisjointAbN<
        T extends DisjointNode<PARENTNODE_T>,
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARTITIONNODE_T extends PartitionedNode,
        PARENTNODE_T extends SinglyRootedNode> extends DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> {
    
    private final PARTITIONNODE_T partitionedNode;
    
    public PartitionedNodeDisjointAbN(DisjointAbstractionNetwork source, PARTITIONNODE_T partitionedNode) {
        
        super(source, new PartitionedNodeDisjointAbNDerivation(
                source.getFactory(), 
                source.getParentAbstractionNetwork().getDerivation(),
                partitionedNode));
        
        this.partitionedNode = partitionedNode;
    }
    
    public PARTITIONNODE_T getSourcePartitionedNode() {
        return partitionedNode;
    }
}
