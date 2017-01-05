package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class SubsetDisjointAbstractionNetwork<
        T extends DisjointNode<PARENTNODE_T>,
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> extends DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T>  {
    
    private final DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceDisjointAbN;
    
    public SubsetDisjointAbstractionNetwork(
            Hierarchy<T> subset,
            Hierarchy<Concept> sourceHierarchy, 
            DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceDisjointAbN) {
        
        super(sourceDisjointAbN.getParentAbstractionNetwork(), 
                subset, 
                sourceHierarchy, 
                sourceDisjointAbN.getLevelCount(), 
                sourceDisjointAbN.getAllSourceNodes(),
                sourceDisjointAbN.getOverlappingNodes());
        
        this.sourceDisjointAbN = sourceDisjointAbN;
    }
    
    public DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSourceDisjointAbN() {
        return sourceDisjointAbN;
    }

    // TODO: Figure out how subsets affect the below, if at all...
    
    @Override
    public SubsetDisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSubsetDisjointAbN(Set<PARENTNODE_T> overlaps) {
        return sourceDisjointAbN.getSubsetDisjointAbN(overlaps);
    }
}
