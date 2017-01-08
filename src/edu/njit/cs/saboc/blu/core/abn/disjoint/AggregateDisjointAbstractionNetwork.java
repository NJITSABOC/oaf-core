package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateDisjointAbstractionNetwork<
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> 
            extends DisjointAbstractionNetwork<AggregateDisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T> 
                implements AggregateAbstractionNetwork<AggregateDisjointNode<PARENTNODE_T>, DisjointAbstractionNetwork<DisjointNode<PARENTNODE_T>, PARENTABN_T, PARENTNODE_T>> {
    
    
    public static final AggregateAncestorDisjointAbN generateAggregateSubsetDisjointAbstractionNetwork(
            DisjointAbstractionNetwork nonAggregateDisjointAbN,
            DisjointAbstractionNetwork superAggregateDisjointAbN,
            AggregateDisjointNode selectedRoot) {
        
        Hierarchy<AggregateDisjointNode> ancestors = superAggregateDisjointAbN.getNodeHierarchy().getAncestorHierarchy(selectedRoot);
        
        Set<DisjointNode> ancestorRoots = new HashSet<>();
        
        ancestors.getRoots().forEach( (root) ->{
            ancestorRoots.add((DisjointNode)root.getAggregatedHierarchy().getRoot());
        });
        
        Set<DisjointNode> aggregatedNodes = new HashSet<>();
        
        ancestors.getNodes().forEach((aggregatePArea) -> {
            aggregatedNodes.addAll(aggregatePArea.getAggregatedHierarchy().getNodes());
        });
        
        //TODO: This can be done with a topological traversal, probably
        Hierarchy<DisjointNode> potentialNodeHierarchy = nonAggregateDisjointAbN.getNodeHierarchy().getAncestorHierarchy(aggregatedNodes);
        
        
        Hierarchy<DisjointNode> actualNodeHierarchy = new Hierarchy<>(
                SetUtilities.getSetIntersection(potentialNodeHierarchy.getRoots(), ancestorRoots));
        
        aggregatedNodes.forEach((parea) -> {
            actualNodeHierarchy.addNode(parea);
        });
        
        potentialNodeHierarchy.getEdges().forEach((edge) -> {
            if(aggregatedNodes.contains(edge.getFrom()) && aggregatedNodes.contains(edge.getTo())) {
                actualNodeHierarchy.addEdge(edge);
            }
        });
        
        Hierarchy<Concept> sourceHierarchy = new Hierarchy(actualNodeHierarchy.getRoot().getRoot());
        
        actualNodeHierarchy.getNodes().forEach( (parea) -> {
            sourceHierarchy.addAllHierarchicalRelationships(parea.getHierarchy());
        });
        
        actualNodeHierarchy.getNodes().forEach((parea) -> {
            nonAggregateDisjointAbN.getSourceHierarchy().getParents(parea.getRoot()).forEach( (p) -> {
                Concept parent = (Concept)p;
                
                if(sourceHierarchy.contains(parent)) {
                    sourceHierarchy.addEdge(parea.getRoot(), parent);
                }
            });
        });
        
        DisjointAbstractionNetwork unaggregatedAncestorAbN = new DisjointAbstractionNetwork(
                nonAggregateDisjointAbN.getParentAbstractionNetwork(), 
                actualNodeHierarchy, 
                sourceHierarchy,
                nonAggregateDisjointAbN.getLevelCount(), 
                nonAggregateDisjointAbN.getAllSourceNodes(), 
                nonAggregateDisjointAbN.getOverlappingNodes());
        
        AggregateAbstractionNetwork agregateAbN = (AggregateAbstractionNetwork)superAggregateDisjointAbN;
        
        DisjointAbstractionNetwork aggregatedAncestorAbN = unaggregatedAncestorAbN.getAggregated(agregateAbN.getAggregateBound());
        
        AncestorDisjointAbN ancestorDisjointAbN = new AncestorDisjointAbN(
            (DisjointNode)selectedRoot.getAggregatedHierarchy().getRoot(), 
            nonAggregateDisjointAbN, 
            unaggregatedAncestorAbN.getNodeHierarchy(), 
            unaggregatedAncestorAbN.getSourceHierarchy());

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
                overlappingNodes);
        
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
