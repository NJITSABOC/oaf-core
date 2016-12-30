package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 */
public class ExpandedDisjointAbN<
        T extends DisjointNode<PARENTNODE_T>,
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> extends DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> {
    
    private final AggregateDisjointAbstractionNetwork<PARENTABN_T, PARENTNODE_T> sourceAbN;
    private final AggregateDisjointNode<PARENTNODE_T> aggregatedNode;
    
    public ExpandedDisjointAbN(AggregateDisjointAbstractionNetwork<PARENTABN_T, PARENTNODE_T> sourceAbN, 
            AggregateDisjointNode<PARENTNODE_T> aggregatedNode) {
        
        super(sourceAbN.getParentAbstractionNetwork(), 
                aggregatedNode.getAggregatedHierarchy(), 
                aggregatedNode.getHierarchy(), 
                sourceAbN.getLevelCount(), 
                sourceAbN.getAllSourceNodes(),
                sourceAbN.getOverlappingNodes());
        
        this.sourceAbN = sourceAbN;
        this.aggregatedNode = aggregatedNode;
    }
    
    public AggregateDisjointAbstractionNetwork<PARENTABN_T, PARENTNODE_T> getSourceAggregateDisjointAbN() {
        return sourceAbN;
    }
    
    public AggregateDisjointNode<PARENTNODE_T> getAggregatedNode() {
        return aggregatedNode;
    }
}
