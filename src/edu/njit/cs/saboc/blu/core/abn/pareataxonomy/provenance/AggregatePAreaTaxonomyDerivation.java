
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;

/**
 * Stores the arguments needed to create an aggregate partial-area taxonomy 
 * 
 * @author Chris O
 */
public class AggregatePAreaTaxonomyDerivation extends PAreaTaxonomyDerivation 
        implements AggregateAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final PAreaTaxonomyDerivation nonAggregateSourceDerivation;
    private final int bound;
    
    public AggregatePAreaTaxonomyDerivation(PAreaTaxonomyDerivation nonAggregateSourceDerivation, int bound) {
        super(nonAggregateSourceDerivation);
        
        this.nonAggregateSourceDerivation = nonAggregateSourceDerivation;
        this.bound = bound;
    }
    
    public AggregatePAreaTaxonomyDerivation(AggregatePAreaTaxonomyDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getNonAggregateSourceDerivation(), deriveTaxonomy.getBound());
    }
    
    @Override
    public PAreaTaxonomyDerivation getNonAggregateSourceDerivation() {
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
