
package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedSubAbN;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DerivedSubsetDisjointAbN extends DerivedDisjointAbN 
        implements DerivedSubAbN<DerivedDisjointAbN> {
    
    private final DerivedDisjointAbN sourceDisjointAbNDerivation;
    private final Set<SinglyRootedNode> subset;
    
    public DerivedSubsetDisjointAbN(
            DerivedDisjointAbN sourceDisjointAbNDerivation, 
            Set<SinglyRootedNode> subset) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.subset = subset;
    }

    @Override
    public DerivedDisjointAbN getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    public Set<SinglyRootedNode> getSubset() {
        return subset;
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        return getSuperAbNDerivation().getAbstractionNetwork().getSubsetDisjointAbN(subset);
    }

    @Override
    public String getDescription() {
        return String.format("%s (subset)", super.getDescription());
    }
}