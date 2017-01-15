package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedAggregateAbN;

/**
 *
 * @author Chris O
 */
public class DerivedAggregateDisjointAbN extends DerivedDisjointAbN 
        implements DerivedAggregateAbN<DerivedDisjointAbN> {
    
    private final DerivedDisjointAbN nonAggregateDerivation;
    private final int aggregateBound;
    
    public DerivedAggregateDisjointAbN(
            DerivedDisjointAbN nonAggregateDerivation, 
            int aggregateBound) {
        
        super(nonAggregateDerivation);
        
        this.nonAggregateDerivation = nonAggregateDerivation;
        this.aggregateBound = aggregateBound;
    }

    @Override
    public int getBound() {
        return aggregateBound;
    }

    @Override
    public DerivedDisjointAbN getNonAggregateSourceDerivation() {
        return nonAggregateDerivation;
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        return nonAggregateDerivation.getAbstractionNetwork().getAggregated(aggregateBound);
    }

    @Override
    public String getDescription() {
        return String.format("%s (aggregate: %d)", super.getDescription(), aggregateBound);
    }
}
