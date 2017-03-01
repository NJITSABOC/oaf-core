package edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris O
 */
public class AggregateDisjointAbNGenerator<
        T extends DisjointNode<PARENTNODE_T>, 
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>, 
        PARENTNODE_T extends SinglyRootedNode> {

    public DisjointAbstractionNetwork createAggregateDisjointAbN(
            DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceDisjointAbN,
            AggregateAbNGenerator<DisjointNode<PARENTNODE_T>, AggregateDisjointNode<PARENTNODE_T>> aggregateGenerator,
            int min) {

        if (min == 1) {
            return sourceDisjointAbN;
        }

        Hierarchy<AggregateDisjointNode<PARENTNODE_T>> reducedNodeHierarchy
                = aggregateGenerator.createAggregateAbN(new AggregateDisjointAbNFactory(),
                        (Hierarchy<DisjointNode<PARENTNODE_T>>)sourceDisjointAbN.getNodeHierarchy(),
                        sourceDisjointAbN.getSourceHierarchy(),
                        min);

        AggregateDisjointAbstractionNetwork<PARENTABN_T, PARENTNODE_T> aggregateDisjointAbN = new AggregateDisjointAbstractionNetwork<>(
                sourceDisjointAbN,
                min,
                sourceDisjointAbN.getParentAbstractionNetwork(),
                reducedNodeHierarchy,
                sourceDisjointAbN.getSourceHierarchy(),
                sourceDisjointAbN.getLevelCount(),
                sourceDisjointAbN.getAllSourceNodes(),
                sourceDisjointAbN.getOverlappingNodes());

        return aggregateDisjointAbN;
    }

    public ExpandedDisjointAbN createExpandedDisjointAbN(
            DisjointAbstractionNetwork sourceAggregateDisjointAbN,
            AggregateDisjointNode aggregateDisjointNode) {

        ExpandedDisjointAbN expandedDisjointAbN = new ExpandedDisjointAbN(
                sourceAggregateDisjointAbN,
                aggregateDisjointNode);

        return expandedDisjointAbN;
    }
}
