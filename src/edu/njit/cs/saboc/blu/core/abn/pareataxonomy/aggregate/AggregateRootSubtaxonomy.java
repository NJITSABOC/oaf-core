package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RootSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregateRootSubtaxonomyDerivation;

/**
 *
 * @author Chris O
 */
public class AggregateRootSubtaxonomy extends RootSubtaxonomy<AggregatePArea> 
        implements AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy> {
    
    private final RootSubtaxonomy nonAggregatedRootSubtaxonomy;
    private final int aggregateBound;
    
    
    public AggregateRootSubtaxonomy(
            PAreaTaxonomy aggregatedSuperAbN, 
            int aggregateBound,
            RootSubtaxonomy nonAggregatedRootSubtaxonomy,
            PAreaTaxonomy subtaxonomy) {
        
        super(aggregatedSuperAbN, 
                subtaxonomy, 
                new AggregateRootSubtaxonomyDerivation(
                        aggregatedSuperAbN.getDerivation(), 
                        aggregateBound, 
                        subtaxonomy.getRootPArea().getRoot()));
        
        this.nonAggregatedRootSubtaxonomy = nonAggregatedRootSubtaxonomy;
        this.aggregateBound = aggregateBound;
    }
    
    public AggregateRootSubtaxonomy(AggregateRootSubtaxonomy subtaxonomy) {
        this(subtaxonomy.getSuperAbN(), 
                subtaxonomy.getAggregateBound(), 
                subtaxonomy.getNonAggregateSourceAbN(), 
                subtaxonomy);
    }
    
    @Override
    public RootSubtaxonomy getNonAggregateSourceAbN() {
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
        return AggregatePAreaTaxonomy.generateAggregatePAreaTaxonomy(this.getNonAggregateSourceAbN(), aggregateBound);
    }

    @Override
    public PAreaTaxonomy createRootSubtaxonomy(AggregatePArea root) {
        return AggregatePAreaTaxonomy.generateAggregateRootSubtaxonomy(this, this.getNonAggregateSourceAbN(), root);
    }
    
    @Override
    public PAreaTaxonomy createAncestorSubtaxonomy(AggregatePArea source) {
        return AggregatePAreaTaxonomy.generateAggregateAncestorSubtaxonomy(this.getNonAggregateSourceAbN(), this, source);
    }
}
