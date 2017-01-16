
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedAggregateAbN;

/**
 *
 * @author Chris O
 */
public class DerivedAggregatePAreaTaxonomy extends DerivedPAreaTaxonomy 
        implements DerivedAggregateAbN<DerivedPAreaTaxonomy> {
    
    private final DerivedPAreaTaxonomy nonAggregateSourceDerivation;
    private final int bound;
    
    public DerivedAggregatePAreaTaxonomy(DerivedPAreaTaxonomy nonAggregateSourceDerivation, int bound) {
        super(nonAggregateSourceDerivation);
        
        this.nonAggregateSourceDerivation = nonAggregateSourceDerivation;
        this.bound = bound;
    }
    
    public DerivedAggregatePAreaTaxonomy(DerivedAggregatePAreaTaxonomy deriveTaxonomy) {
        this(deriveTaxonomy.getNonAggregateSourceDerivation(), deriveTaxonomy.getBound());
    }
    
    @Override
    public DerivedPAreaTaxonomy getNonAggregateSourceDerivation() {
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
    public PAreaTaxonomy getAbstractionNetwork() {
        return getNonAggregateSourceDerivation().getAbstractionNetwork().getAggregated(bound);
    }
}
