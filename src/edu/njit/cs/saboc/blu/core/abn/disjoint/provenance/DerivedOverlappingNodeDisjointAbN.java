package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedSubAbN;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DerivedOverlappingNodeDisjointAbN<T extends SinglyRootedNode> extends DerivedDisjointAbN<T> 
        implements DerivedSubAbN<DerivedDisjointAbN> {
    
    private final DerivedDisjointAbN sourceDisjointAbNDerivation;
    private final T overlappingNode;
    
    public DerivedOverlappingNodeDisjointAbN(
            DerivedDisjointAbN sourceDisjointAbNDerivation, 
            T overlappingNode) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.overlappingNode = overlappingNode;
    }

    public T getOverlappingNode() {
        return overlappingNode;
    }
    
    @Override
    public DerivedDisjointAbN<T> getSuperAbNDerivation() {
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