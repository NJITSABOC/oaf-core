package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedAggregateAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;

/**
 *
 * @author Chris O
 */
public class DerivedAggregateTargetAbN extends DerivedTargetAbN 
    implements DerivedAggregateAbN<DerivedTargetAbN> {
    
    private final DerivedTargetAbN nonAggregateSource;
    private final int bound;
    
    public DerivedAggregateTargetAbN(DerivedTargetAbN nonAggregateSource, int bound) {
        super(nonAggregateSource);
        
        this.nonAggregateSource = nonAggregateSource;
        this.bound = bound;
    }

    @Override
    public int getBound() {
        return bound;
    }

    @Override
    public DerivedTargetAbN getNonAggregateSourceDerivation() {
        return nonAggregateSource;
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork() {
        TargetAbstractionNetwork targetAbN = this.getNonAggregateSourceDerivation().getAbstractionNetwork();
        
        return targetAbN.getAggregated(bound);
    }

    @Override
    public String getDescription() {
        return String.format("%s (aggregated: %d)", super.getDescription(), bound);
    }
}
