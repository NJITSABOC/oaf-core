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
    
    private boolean isAggregated;
    
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
        
        this.isAggregated = false;
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

    protected void setAggregated(boolean value) {
        this.isAggregated = value;
    }
    
    @Override
    public boolean isAggregated() {
        return isAggregated;
    }

    @Override
    public DisjointAbstractionNetwork getAggregated(int smallestNode) {
        AggregateDisjointAbNGenerator generator = new AggregateDisjointAbNGenerator();
        
        AggregateAbNGenerator<DisjointNode<PARENTNODE_T>, AggregateDisjointNode<PARENTNODE_T>> aggregateGenerator = new AggregateAbNGenerator<>();
        
        return generator.createAggregateDisjointAbN(this, aggregateGenerator, smallestNode);
    }
    
    public AncestorDisjointAbN<T, PARENTABN_T, PARENTNODE_T> getAncestorDisjointAbN(T root) {
        Hierarchy<T> ancestorSubhierarchy = this.getNodeHierarchy().getAncestorHierarchy(root);
        
        Hierarchy<Concept> conceptSubhierarchy = getSubhierarchyConcepts(ancestorSubhierarchy);
        
        return new AncestorDisjointAbN<>(root, this, ancestorSubhierarchy, conceptSubhierarchy);
    }
    
    public SubsetDisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSubsetDisjointAbN(Set<PARENTNODE_T> overlaps) {
        
        SubsetDisjointAbNSubhierarchyVisitor visitor = 
                new SubsetDisjointAbNSubhierarchyVisitor<>(
                        (Hierarchy<DisjointNode<PARENTNODE_T>>)this.getNodeHierarchy(), 
                        overlaps);
        
        this.getNodeHierarchy().topologicalDown(visitor);

        Hierarchy<T> subsetSubhierarchy = (Hierarchy<T>)visitor.getSubsetSubhierarchy();
        
        Hierarchy<Concept> conceptSubhierarchy = getSubhierarchyConcepts(subsetSubhierarchy);
        
        return new SubsetDisjointAbstractionNetwork<>(subsetSubhierarchy, conceptSubhierarchy, this);
    }
    
    private Hierarchy<Concept> getSubhierarchyConcepts(Hierarchy<T> disjointNodeSubhierarchy) {
        Set<Concept> roots = new HashSet<>();
        
        disjointNodeSubhierarchy.getRoots().forEach( (rootNode) -> {
            roots.add(rootNode.getRoot());
        });
        
        Hierarchy<Concept> conceptSubhierarchy = new Hierarchy<>(roots);
        
        disjointNodeSubhierarchy.getNodes().forEach( (node) -> {
            conceptSubhierarchy.addAllHierarchicalRelationships(node.getHierarchy());
        });
        
        disjointNodeSubhierarchy.getNodes().forEach( (node) -> {            
            this.getSourceHierarchy().getParents(node.getRoot()).forEach( (parent) -> {
               if(conceptSubhierarchy.contains(parent)) {
                   conceptSubhierarchy.addEdge(node.getRoot(), parent);
               } 
            });
        });
        
        return conceptSubhierarchy;
    }
}
