package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedSubAbN;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DerivedOverlappingNodeDisjointAbN extends DerivedDisjointAbN 
        implements DerivedSubAbN<DerivedDisjointAbN> {
    
    private final DerivedDisjointAbN sourceDisjointAbNDerivation;
    private final SinglyRootedNode overlappingNode;
    
    public DerivedOverlappingNodeDisjointAbN(
            DerivedDisjointAbN sourceDisjointAbNDerivation, 
            SinglyRootedNode overlappingNode) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.overlappingNode = overlappingNode;
    }

    public SinglyRootedNode getOverlappingNode() {
        return overlappingNode;
    }
    
    @Override
    public DerivedDisjointAbN getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        DisjointAbstractionNetwork disjointAbN = getSuperAbNDerivation().getAbstractionNetwork();
        
        return disjointAbN.getOverlappingNodeDisjointAbN(overlappingNode);
    }

    @Override
    public String getDescription() {
        return String.format("%s (overlapping node)", super.getDescription());
    }
}