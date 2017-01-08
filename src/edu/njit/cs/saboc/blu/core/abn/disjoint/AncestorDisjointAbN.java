package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AncestorDisjointAbN <
        T extends DisjointNode<PARENTNODE_T>,
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> extends DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> {
    
    private final T selectedRoot;
    
    private final DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceDisjointAbN;
    
    public AncestorDisjointAbN(
            T selectedRoot,
            DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceDisjointAbN,
            Hierarchy<T> nodeAncestorSubhierarchy,
            Hierarchy<Concept> sourceSubhierarchy) {
        
        super(sourceDisjointAbN.getParentAbstractionNetwork(), 
                nodeAncestorSubhierarchy, 
                sourceSubhierarchy, 
                sourceDisjointAbN.getLevelCount(), 
                sourceDisjointAbN.getAllSourceNodes(), 
                sourceDisjointAbN.getOverlappingNodes());
        
        this.selectedRoot = selectedRoot;
        this.sourceDisjointAbN = sourceDisjointAbN;
        
        this.setAggregated(sourceDisjointAbN.isAggregated());
    }
    
    public T getSelectedRoot() {
        return selectedRoot;
    }
    
    public DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSourceDisjointAbN() {
        return sourceDisjointAbN;
    }
}