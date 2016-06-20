package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.MultiRootedConceptHierarchy;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Chris
 */
public class DisjointAbstractionNetwork<
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> extends AbstractionNetwork<DisjointNode<PARENTNODE_T>> {
    
    private final Set<PARENTNODE_T> allNodes;
    
    private final Set<PARENTNODE_T> overlappingNodes;
    
    private final PARENTABN_T parentAbN;
    
    private final MultiRootedConceptHierarchy sourceHierarchy;
    
    private final int levels;
    
    public DisjointAbstractionNetwork(PARENTABN_T parentAbN, 
            NodeHierarchy<DisjointNode<PARENTNODE_T>> groupHierarchy,
            MultiRootedConceptHierarchy sourceHierarchy,
            int levels,
            Set<PARENTNODE_T> allNodes,
            Set<PARENTNODE_T> overlappingNodes) {
        
        super(groupHierarchy);
        
        this.parentAbN = parentAbN;
        
        this.sourceHierarchy = sourceHierarchy;
        
        this.allNodes = allNodes;
        
        this.overlappingNodes = overlappingNodes;
        
        this.levels = levels;
    }
    
    public PARENTABN_T getParentAbstractionNetwork() {
        return parentAbN;
    }
    
    public Set<PARENTNODE_T> getOverlappingNodes() {
        return overlappingNodes;
    }
    
    public MultiRootedConceptHierarchy getSourceHierarchy() {
        return sourceHierarchy;
    }
    
    public Set<DisjointNode<PARENTNODE_T>> getAllDisjointNodes() {
        return (Set<DisjointNode<PARENTNODE_T>>)super.getNodeHierarchy();
    }
    
    public Set<DisjointNode<PARENTNODE_T>> getOverlappingDisjointNodes() {
        Set<DisjointNode<PARENTNODE_T>> allDisjointNodes = getAllDisjointNodes();
        
        Set<DisjointNode<PARENTNODE_T>> overlappingDisjointNodes = 
                allDisjointNodes.stream().filter( (disjointNode) -> {
                    return disjointNode.getOverlaps().size() > 1;
                }).collect(Collectors.toSet());
        
        return overlappingDisjointNodes;
    }
    
    public Set<DisjointNode<PARENTNODE_T>> getNonOverlappingDisjointNodes() {
        Set<DisjointNode<PARENTNODE_T>> allDisjointNodes = getAllDisjointNodes();
        
        Set<DisjointNode<PARENTNODE_T>> nonOverlappingDisjointNodes = 
                allDisjointNodes.stream().filter( (disjointNode) -> {
                    return disjointNode.getOverlaps().size() == 1;
                }).collect(Collectors.toSet());
        
        return nonOverlappingDisjointNodes;
    }

    public int getLevelCount() {
        return levels;
    }
    
    public Set<DisjointNode<PARENTNODE_T>> getRoots() {
        return super.getNodeHierarchy().getRoots();
    }
}
