
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.pravenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;

/**
 *
 * @author Chris O
 */
public class DerivedAggregatePAreaTaxonomy extends DerivedPAreaTaxonomy {
    private final DerivedPAreaTaxonomy source;
    private final int bound;
    
    public DerivedAggregatePAreaTaxonomy(DerivedPAreaTaxonomy source, int bound) {
        super(source);
        
        this.source = source;
        this.bound = bound;
    }

    @Override
    public String getDescription() {
        return String.format("%s (aggregated: %d)", super.getDescription(), bound);
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        return super.getAbstractionNetwork().getAggregated(bound);
    }
}
