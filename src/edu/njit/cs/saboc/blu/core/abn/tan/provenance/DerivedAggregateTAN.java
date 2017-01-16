
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedAggregateAbN;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;

/**
 *
 * @author Chris O
 */
public class DerivedAggregateTAN extends DerivedClusterTAN 
        implements DerivedAggregateAbN<DerivedClusterTAN> {
    
    private final DerivedClusterTAN nonAggregateSourceDerivation;
    private final int bound;
    
    public DerivedAggregateTAN(DerivedClusterTAN nonAggregateSourceDerivation, int bound) {
        super(nonAggregateSourceDerivation);
        
        this.nonAggregateSourceDerivation = nonAggregateSourceDerivation;
        this.bound = bound;
    }
    
    public DerivedAggregateTAN(DerivedAggregateTAN deriveTaxonomy) {
        this(deriveTaxonomy.getNonAggregateSourceDerivation(), deriveTaxonomy.getBound());
    }
    
    @Override
    public DerivedClusterTAN getNonAggregateSourceDerivation() {
        return nonAggregateSourceDerivation;
    }
    
    @Override
    public int getBound() {
        return bound;
    }

    @Override
    public String getDescription() {
        return String.format("%s (aggregated: %d)", nonAggregateSourceDerivation.getDescription(), bound);
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        return getNonAggregateSourceDerivation().getAbstractionNetwork().getAggregated(bound);
    }
}
