package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.SubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.DerivedExpandedSubtaxonomy;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ExpandedSubtaxonomy<T extends PAreaTaxonomy & AggregateAbstractionNetwork> 
            extends PAreaTaxonomy implements SubAbstractionNetwork<T> {
    
    private final T superTaxonomy;
    private final AggregatePArea aggregatePArea;
    
    public ExpandedSubtaxonomy(
            T superTaxonomy,
            AggregatePArea aggregatePArea,
            PAreaTaxonomy expandedSubtaxonomy) {

        super(expandedSubtaxonomy, new DerivedExpandedSubtaxonomy(superTaxonomy.getDerivation(), aggregatePArea.getRoot()));
        
        this.superTaxonomy = superTaxonomy;
        this.aggregatePArea = aggregatePArea;
    }
    
    @Override
    public DerivedExpandedSubtaxonomy getDerivation() {
        return (DerivedExpandedSubtaxonomy)super.getDerivation();
    }

    @Override
    public T getSuperAbN() {
        return superTaxonomy;
    }

    public AggregatePArea getExpandedAggregatePArea() {
        return aggregatePArea;
    }
}