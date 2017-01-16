
package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedSubAbN;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DerivedSubsetDisjointAbN<T extends SinglyRootedNode> extends DerivedDisjointAbN<T> 
        implements DerivedSubAbN<DerivedDisjointAbN> {
    
    private final DerivedDisjointAbN sourceDisjointAbNDerivation;
    private final Set<T> subset;
    
    public DerivedSubsetDisjointAbN(
            DerivedDisjointAbN sourceDisjointAbNDerivation, 
            Set<T> subset) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.subset = subset;
    }

    @Override
    public DerivedDisjointAbN<T> getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    public Set<T> getSubset() {
        return subset;
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        return getSuperAbNDerivation().getAbstractionNetwork().getSubsetDisjointAbN(subset);
    }

    @Override
    public String getDescription() {
        return String.format("%s (subset)", sourceDisjointAbNDerivation.getDescription());
    }
}