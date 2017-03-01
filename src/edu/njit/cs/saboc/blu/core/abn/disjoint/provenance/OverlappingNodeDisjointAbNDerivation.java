package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;

/**
 * Stores the arguments needed to create a disjoint abstraction network based 
 * on the selection of an overlapping singly rooted node.
 * 
 * @author Chris O
 * @param <T>
 */
public class OverlappingNodeDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation<T> 
        implements SubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final T overlappingNode;
    
    public OverlappingNodeDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            T overlappingNode) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.overlappingNode = overlappingNode;
    }

    public T getOverlappingNode() {
        return overlappingNode;
    }
    
    @Override
    public DisjointAbNDerivation<T> getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        DisjointAbstractionNetwork disjointAbN = getSuperAbNDerivation().getAbstractionNetwork();
        
        return disjointAbN.getOverlappingNodeDisjointAbN(overlappingNode);
    }

    @Override
    public String getDescription() {
        return String.format("%s (overlapping node)", sourceDisjointAbNDerivation.getDescription());
    }
}