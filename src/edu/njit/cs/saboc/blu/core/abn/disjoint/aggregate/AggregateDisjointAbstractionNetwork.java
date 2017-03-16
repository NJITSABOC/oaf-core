package edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.AncestorDisjointAbN;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.AggregateDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * A class for representing aggregate disjoint abstraction networks
 * 
 * @author Chris O
 * @param <PARENTABN_T>
 * @param <PARENTNODE_T>
 */
public class AggregateDisjointAbstractionNetwork<
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> 
            extends DisjointAbstractionNetwork<AggregateDisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T> 
                implements AggregateAbstractionNetwork<AggregateDisjointNode<PARENTNODE_T>, DisjointAbstractionNetwork<DisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T>> {
    
    
    /**
     * Static method for generating aggregate disjoint abstraction networks 
     * that consist of a selected aggregate disjoint node and all of its  
     * aggregate disjoint node ancestors
     * 
     * @param nonAggregateDisjointAbN The non-aggregate version of the source disjoint abstraction network
     * @param superAggregateDisjointAbN The aggregate disjoint abstraction network
     * @param selectedRoot The selected aggregate disjoint node
     * @return 
     */
    public static final AggregateAncestorDisjointAbN generateAggregateSubsetDisjointAbstractionNetwork(
            DisjointAbstractionNetwork nonAggregateDisjointAbN,
            DisjointAbstractionNetwork superAggregateDisjointAbN,
            AggregateDisjointNode selectedRoot) {

        Hierarchy<DisjointNode> actualNodeHierarchy = AbstractionNetworkUtils.getDeaggregatedAncestorHierarchy(
                nonAggregateDisjointAbN.getNodeHierarchy(), 
                superAggregateDisjointAbN.getNodeHierarchy().getAncestorHierarchy(selectedRoot));
               
        Hierarchy<Concept> sourceHierarchy = AbstractionNetworkUtils.getConceptHierarchy(
                actualNodeHierarchy, 
                nonAggregateDisjointAbN.getSourceHierarchy());
        
        DisjointAbstractionNetwork unaggregatedAncestorAbN = new DisjointAbstractionNetwork(
                nonAggregateDisjointAbN.getParentAbstractionNetwork(), 
                actualNodeHierarchy, 
                sourceHierarchy,
                nonAggregateDisjointAbN.getLevelCount(), 
                nonAggregateDisjointAbN.getAllSourceNodes(), 
                nonAggregateDisjointAbN.getOverlappingNodes(), 
                null);
        
        AggregateAbstractionNetwork agregateAbN = (AggregateAbstractionNetwork)superAggregateDisjointAbN;
        
        DisjointAbstractionNetwork aggregatedAncestorAbN = unaggregatedAncestorAbN.getAggregated(agregateAbN.getAggregateBound());
        
        AncestorDisjointAbN ancestorDisjointAbN = new AncestorDisjointAbN(
            (DisjointNode)selectedRoot.getAggregatedHierarchy().getRoot(), 
            nonAggregateDisjointAbN, 
            unaggregatedAncestorAbN.getNodeHierarchy(), 
            unaggregatedAncestorAbN.getSourceHierarchy(), 
            null);

        return new AggregateAncestorDisjointAbN(
                selectedRoot, 
                superAggregateDisjointAbN,
                agregateAbN.getAggregateBound(),
                ancestorDisjointAbN,
                aggregatedAncestorAbN);
    }
    
    
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
                overlappingNodes,
                new AggregateDisjointAbNDerivation(sourceAbN.getDerivation(), aggregateBound));
        
        this.sourceAbN = sourceAbN;
        this.aggregateBound = aggregateBound;
    }

    @Override
    public DisjointAbstractionNetwork<DisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T> getNonAggregateSourceAbN() {
        return sourceAbN;
    }

    @Override
    public int getAggregateBound() {
        return aggregateBound;
    }
    
    @Override
    public boolean isAggregated() {
        return true;
    }
    
    @Override
    public DisjointAbstractionNetwork getAggregated(int smallestNode) {
        AggregateDisjointAbNGenerator generator = new AggregateDisjointAbNGenerator();
        
        AggregateAbNGenerator<DisjointNode<PARENTNODE_T>, AggregateDisjointNode<PARENTNODE_T>> aggregateGenerator = 
                new AggregateAbNGenerator<>();
        
        return generator.createAggregateDisjointAbN(this.getNonAggregateSourceAbN(), aggregateGenerator, smallestNode);
    }
    
    @Override
    public ExpandedDisjointAbN expandAggregateNode(AggregateDisjointNode<PARENTNODE_T> aggregateNode) {
        return new AggregateDisjointAbNGenerator().createExpandedDisjointAbN(this, aggregateNode);
    }

    @Override
    public AggregateAncestorDisjointAbN<PARENTABN_T, PARENTNODE_T> getAncestorDisjointAbN(AggregateDisjointNode<PARENTNODE_T> root) {
        
        return AggregateDisjointAbstractionNetwork.generateAggregateSubsetDisjointAbstractionNetwork(this.getNonAggregateSourceAbN(), 
                this, 
                root);
        
    }
}
