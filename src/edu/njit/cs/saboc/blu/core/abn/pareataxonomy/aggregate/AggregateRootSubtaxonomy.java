package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RootSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregateRootSubtaxonomyDerivation;

/**
 * An aggregate partial-area taxonomy that consists of a chosen 
 * aggregate partial-area and all of its descendant aggregate partial-areas
 * 
 * @author Chris O
 */
public class AggregateRootSubtaxonomy extends RootSubtaxonomy<AggregatePArea> 
        implements AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy> {
    
    private final RootSubtaxonomy nonAggregatedRootSubtaxonomy;
    private final int aggregateBound;
    private final boolean weightedAggregated;
    
    
    public AggregateRootSubtaxonomy(
            PAreaTaxonomy aggregatedSuperAbN, 
            int aggregateBound,
            RootSubtaxonomy nonAggregatedRootSubtaxonomy,
            PAreaTaxonomy subtaxonomy,
            boolean weightedAggregated) {
        
        super(aggregatedSuperAbN, 
                subtaxonomy, 
                new AggregateRootSubtaxonomyDerivation(
                        aggregatedSuperAbN.getDerivation(), 
                        aggregateBound, 
                        subtaxonomy.getRootPArea().getRoot()));
        
        this.nonAggregatedRootSubtaxonomy = nonAggregatedRootSubtaxonomy;
        this.aggregateBound = aggregateBound;
        this.weightedAggregated = weightedAggregated;
    }
    
    public AggregateRootSubtaxonomy(AggregateRootSubtaxonomy subtaxonomy) {
        this(subtaxonomy.getSuperAbN(), 
                subtaxonomy.getAggregateBound(), 
                subtaxonomy.getNonAggregateSourceAbN(), 
                subtaxonomy,
                subtaxonomy.weightedAggregated);
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
        return AggregatePAreaTaxonomy.generateAggregatePAreaTaxonomy(this.getNonAggregateSourceAbN(), aggregateBound, false);
    }
      
    @Override
    public PAreaTaxonomy getWeightedAggregated(int aggregateBound, boolean weightedAggregated) {
        return AggregatePAreaTaxonomy.generateAggregatePAreaTaxonomy(this.getNonAggregateSourceAbN(), aggregateBound, weightedAggregated);
    }

    @Override
    public PAreaTaxonomy createRootSubtaxonomy(AggregatePArea root) {
        return AggregatePAreaTaxonomy.generateAggregateRootSubtaxonomy(this, this.getNonAggregateSourceAbN(), root);
    }
    
    @Override
    public PAreaTaxonomy createAncestorSubtaxonomy(AggregatePArea source) {
        return AggregatePAreaTaxonomy.generateAggregateAncestorSubtaxonomy(this.getNonAggregateSourceAbN(), this, source);
    }
    
    @Override
    public AggregatedProperty getAggregatedProperty(){
        return new AggregatedProperty(aggregateBound, weightedAggregated);
    }
}
