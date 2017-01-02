package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Chris
 */
public class DisjointAbstractionNetwork<
        T extends DisjointNode<PARENTNODE_T>,
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> 
            extends AbstractionNetwork<T>

        implements AggregateableAbstractionNetwork<DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T>> {
    
    private final Set<PARENTNODE_T> allNodes;
    
    private final Set<PARENTNODE_T> overlappingNodes;
    
    private final PARENTABN_T parentAbN;
    
    private final int levels;
    
    public DisjointAbstractionNetwork(PARENTABN_T parentAbN, 
            Hierarchy<T> groupHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            int levels,
            Set<PARENTNODE_T> allNodes,
            Set<PARENTNODE_T> overlappingNodes) {
        
        super(groupHierarchy, sourceHierarchy);
        
        this.parentAbN = parentAbN;
                
        this.allNodes = allNodes;
        
        this.overlappingNodes = overlappingNodes;
        
        this.levels = levels;
    }
    
    public PARENTABN_T getParentAbstractionNetwork() {
        return parentAbN;
    }
    
    public Set<PARENTNODE_T> getAllSourceNodes() {
        return allNodes;
    }
    
    public Set<PARENTNODE_T> getOverlappingNodes() {
        return overlappingNodes;
    }
    
    public Set<T> getAllDisjointNodes() {
        return super.getNodeHierarchy().getNodes();
    }
    
    public Set<T> getOverlappingDisjointNodes() {
        Set<T> allDisjointNodes = getAllDisjointNodes();
        
        Set<T> overlappingDisjointNodes = 
                allDisjointNodes.stream().filter( (disjointNode) -> {
                    return disjointNode.getOverlaps().size() > 1;
                }).collect(Collectors.toSet());
        
        return overlappingDisjointNodes;
    }
    
    public Set<T> getNonOverlappingDisjointNodes() {
        Set<T> allDisjointNodes = getAllDisjointNodes();
        
        Set<T> nonOverlappingDisjointNodes = 
                allDisjointNodes.stream().filter( (disjointNode) -> {
                    return disjointNode.getOverlaps().size() == 1;
                }).collect(Collectors.toSet());
        
        return nonOverlappingDisjointNodes;
    }

    public int getLevelCount() {
        return levels;
    }
    
    public Set<T> getRoots() {
        return super.getNodeHierarchy().getRoots();
    }

    @Override
    public Set<ParentNodeDetails<T>> getParentNodeDetails(T node) {
        return AbstractionNetworkUtils.getSinglyRootedNodeParentNodeDetails(
                node, 
                this.getSourceHierarchy(), 
                this.getAllDisjointNodes());
    }

    @Override
    public boolean isAggregated() {
        return false;
    }

    @Override
    public DisjointAbstractionNetwork getAggregated(int smallestNode) {
        AggregateDisjointAbNGenerator generator = new AggregateDisjointAbNGenerator();
        
        AggregateAbNGenerator<DisjointNode<PARENTNODE_T>, AggregateDisjointNode<PARENTNODE_T>> aggregateGenerator = new AggregateAbNGenerator<>();
        
        return generator.createAggregateDisjointAbN(this, aggregateGenerator, smallestNode);
    }
    
    public SubsetDisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSubsetDisjointAbN(Set<PARENTNODE_T> overlaps) {
        
        SubsetDisjointAbNSubhierarchyVisitor visitor = 
                new SubsetDisjointAbNSubhierarchyVisitor<>(
                        (Hierarchy<DisjointNode<PARENTNODE_T>>)this.getNodeHierarchy(), 
                        overlaps);
        
        this.getNodeHierarchy().topologicalDown(visitor);

        Hierarchy<T> subsetSubhierarchy = (Hierarchy<T>)visitor.getSubsetSubhierarchy();
        
        System.out.println(subsetSubhierarchy.getNodes().size());
        
        Set<Concept> roots = new HashSet<>();
        
        subsetSubhierarchy.getRoots().forEach( (rootNode) -> {
            roots.add(rootNode.getRoot());
        });
        
        Hierarchy<Concept> conceptHierarchy = new Hierarchy<>(roots);
        
        subsetSubhierarchy.getNodes().forEach( (node) -> {
            conceptHierarchy.addAllHierarchicalRelationships(node.getHierarchy());
        });
        
        subsetSubhierarchy.getNodes().forEach( (node) -> {            
            this.getSourceHierarchy().getParents(node.getRoot()).forEach( (parent) -> {
               if(conceptHierarchy.contains(parent)) {
                   conceptHierarchy.addEdge(node.getRoot(), parent);
               } 
            });
        });
        
        return new SubsetDisjointAbstractionNetwork<>(subsetSubhierarchy, conceptHierarchy, this);
    }
}
