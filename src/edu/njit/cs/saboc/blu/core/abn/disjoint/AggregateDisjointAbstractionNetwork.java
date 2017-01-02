package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateDisjointAbstractionNetwork<
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> extends DisjointAbstractionNetwork<AggregateDisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T> 

            implements AggregateAbstractionNetwork<DisjointAbstractionNetwork<DisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T>> {
    
    private final DisjointAbstractionNetwork<DisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T> sourceAbN;
    
    private final int aggregateBound;
    
    public AggregateDisjointAbstractionNetwork(
            DisjointAbstractionNetwork sourceAbN,
            int aggregateBound, 
            PARENTABN_T parentAbN, 
            Hierarchy<AggregateDisjointNode<PARENTNODE_T>> groupHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            int levels,
            Set<PARENTNODE_T> allNodes,
            Set<PARENTNODE_T> overlappingNodes) {
        
        super(parentAbN, 
                groupHierarchy, 
                sourceHierarchy, 
                levels, 
                allNodes, 
                overlappingNodes);
        
        this.sourceAbN = sourceAbN;
        this.aggregateBound = aggregateBound;
    }

    @Override
    public DisjointAbstractionNetwork<DisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T> getSource() {
        return sourceAbN;
    }

    @Override
    public int getBound() {
        return aggregateBound;
    }
    
    @Override
    public boolean isAggregated() {
        return true;
    }
    
    @Override
    public DisjointAbstractionNetwork getAggregated(int smallestNode) {
        AggregateDisjointAbNGenerator generator = new AggregateDisjointAbNGenerator();
        
        AggregateAbNGenerator<DisjointNode<PARENTNODE_T>, AggregateDisjointNode<PARENTNODE_T>> aggregateGenerator = new AggregateAbNGenerator<>();
        
        return generator.createAggregateDisjointAbN(this.getSource(), aggregateGenerator, smallestNode);
    }
    
    public ExpandedDisjointAbN getExpandedDisjointAbN(AggregateDisjointNode<PARENTNODE_T> aggregateNode) {
        return new AggregateDisjointAbNGenerator().createExpandedDisjointAbN(this, aggregateNode);
    }
}
