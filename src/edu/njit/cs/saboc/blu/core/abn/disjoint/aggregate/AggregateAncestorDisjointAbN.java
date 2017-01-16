package edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.AncestorDisjointAbN;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.DerivedAggregateAncestorTAN;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 */
public class AggregateAncestorDisjointAbN<
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode>
            extends AncestorDisjointAbN<AggregateDisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T> 

            implements AggregateAbstractionNetwork<AggregateDisjointNode<PARENTNODE_T>, DisjointAbstractionNetwork<DisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T>> {
    
    private final int aggregateBound;
    private final AncestorDisjointAbN nonAggregatedDisjointAbN;
    
    public AggregateAncestorDisjointAbN(
            AggregateDisjointNode<PARENTNODE_T> selectedRoot,
            DisjointAbstractionNetwork aggregatedSuperAbN, 
            int aggregateBound,
            AncestorDisjointAbN nonAggregatedDisjointAbN,
            DisjointAbstractionNetwork subAbN) {
        
        super(selectedRoot, 
                aggregatedSuperAbN, 
                subAbN.getNodeHierarchy(), 
                nonAggregatedDisjointAbN.getSourceHierarchy(), 
                new DerivedAggregateAncestorTAN(aggregatedSuperAbN.getDerivation(), aggregateBound, selectedRoot.getRoot()));
        
        this.nonAggregatedDisjointAbN = nonAggregatedDisjointAbN;
        this.aggregateBound = aggregateBound;
    }

    @Override
    public AncestorDisjointAbN getNonAggregateSourceAbN() {
        return nonAggregatedDisjointAbN;
    }

    @Override
    public int getAggregateBound() {
        return aggregateBound;
    }

    @Override
    public DisjointAbstractionNetwork<DisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T> expandAggregateNode(
            AggregateDisjointNode<PARENTNODE_T> aggregateNode) {
        
        return new AggregateDisjointAbNGenerator().createExpandedDisjointAbN(this, aggregateNode);
    }

    @Override
    public boolean isAggregated() {
        return true;
    }

    @Override
    public AncestorDisjointAbN<AggregateDisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T> getAncestorDisjointAbN(AggregateDisjointNode<PARENTNODE_T> root) {
     
        return AggregateDisjointAbstractionNetwork.generateAggregateSubsetDisjointAbstractionNetwork(this.getNonAggregateSourceAbN(), 
                this, 
                (AggregateDisjointNode)root);
    }
}
