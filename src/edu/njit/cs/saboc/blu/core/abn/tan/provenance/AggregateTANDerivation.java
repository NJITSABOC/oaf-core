
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;

/**
 * Stores the arguments needed to create an aggregate cluster TAN
 * 
 * @author Chris O
 */
public class AggregateTANDerivation extends ClusterTANDerivation 
        implements AggregateAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation nonAggregateSourceDerivation;
    private final int bound;
    
    public AggregateTANDerivation(ClusterTANDerivation nonAggregateSourceDerivation, int bound) {
        super(nonAggregateSourceDerivation);
        
        this.nonAggregateSourceDerivation = nonAggregateSourceDerivation;
        this.bound = bound;
    }
    
    public AggregateTANDerivation(AggregateTANDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getNonAggregateSourceDerivation(), deriveTaxonomy.getBound());
    }
    
    @Override
    public ClusterTANDerivation getNonAggregateSourceDerivation() {
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
