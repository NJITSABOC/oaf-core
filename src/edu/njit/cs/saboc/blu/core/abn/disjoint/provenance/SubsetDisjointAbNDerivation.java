
package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import java.util.Set;

/**
 * Stores the arguments needed to create a subset disjoint abstraction network
 * 
 * @author Chris O
 * @param <T>
 */
public class SubsetDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation<T> 
        implements SubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final Set<T> subset;
    
    public SubsetDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            Set<T> subset) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.subset = subset;
    }

    @Override
    public DisjointAbNDerivation<T> getSuperAbNDerivation() {
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