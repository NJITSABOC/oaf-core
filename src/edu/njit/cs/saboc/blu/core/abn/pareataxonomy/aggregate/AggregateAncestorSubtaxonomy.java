package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;

/**
 *
 * @author Chris O
 */
public class AggregateAncestorSubtaxonomy extends AncestorSubtaxonomy<AggregatePArea> 
        implements AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy> {
    
    private final AncestorSubtaxonomy nonAggregatedRootSubtaxonomy;
    private final int aggregateBound;
    
    public AggregateAncestorSubtaxonomy(
            PAreaTaxonomy aggregatedSuperAbN, 
            int aggregateBound,
            AggregatePArea selectedRoot,
            AncestorSubtaxonomy nonAggregatedRootSubtaxonomy,
            PAreaTaxonomy subtaxonomy) {
        
        super(aggregatedSuperAbN, selectedRoot, subtaxonomy);
        
        this.nonAggregatedRootSubtaxonomy = nonAggregatedRootSubtaxonomy;
        this.aggregateBound = aggregateBound;
    }

    @Override
    public AncestorSubtaxonomy getNonAggregateSource() {
        return nonAggregatedRootSubtaxonomy;
    }

    @Override
    public int getAggregateBound() {
        return aggregateBound;
    }

    @Override
    public boolean isAggregated() {
        return true;
    }
    
    @Override
    public PAreaTaxonomy expandAggregateNode(AggregatePArea parea) {
       return AggregatePAreaTaxonomy.generateExpandedSubtaxonomy(this, parea);
    }
    
    @Override
    public PAreaTaxonomy getAggregated(int aggregateBound) {
        return AggregatePAreaTaxonomy.generateAggregatePAreaTaxonomy(this.getNonAggregateSource(), aggregateBound);
    }

    @Override
    public PAreaTaxonomy createRootSubtaxonomy(AggregatePArea root) {
        return AggregatePAreaTaxonomy.generateAggregateRootSubtaxonomy(this, this.getNonAggregateSource(), root);
    }
    
    @Override
    public PAreaTaxonomy createAncestorSubtaxonomy(AggregatePArea source) {
        return AggregatePAreaTaxonomy.generateAggregateAncestorSubtaxonomy(this.getNonAggregateSource(), this, source);
    }
}
