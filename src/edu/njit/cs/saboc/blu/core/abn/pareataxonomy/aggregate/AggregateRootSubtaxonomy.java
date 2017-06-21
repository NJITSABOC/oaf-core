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
    private final boolean isWeightedAggregated;
    
    public AggregateRootSubtaxonomy(
            PAreaTaxonomy aggregatedSuperAbN, 
            AggregatedProperty aggregatedProperty,
            RootSubtaxonomy nonAggregatedRootSubtaxonomy,
            PAreaTaxonomy subtaxonomy) {
        
        super(aggregatedSuperAbN, 
                subtaxonomy, 
                new AggregateRootSubtaxonomyDerivation(
                        aggregatedSuperAbN.getDerivation(), 
                        aggregatedProperty, 
                        subtaxonomy.getRootPArea().getRoot()
                ));
        
        this.nonAggregatedRootSubtaxonomy = nonAggregatedRootSubtaxonomy;
        this.aggregateBound = aggregatedProperty.getBound();
        this.isWeightedAggregated = aggregatedProperty.getWeighted();
    }
    
    public AggregateRootSubtaxonomy(AggregateRootSubtaxonomy subtaxonomy) {
        this(subtaxonomy.getSuperAbN(), 
                subtaxonomy.getAggregatedProperty(), 
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
    public PAreaTaxonomy getAggregated(int aggregateBound, boolean isWeightedAggregated) {
        return AggregatePAreaTaxonomy.generateAggregatePAreaTaxonomy(this.getNonAggregateSourceAbN(), new AggregatedProperty(aggregateBound, isWeightedAggregated));
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
        return new AggregatedProperty(aggregateBound, isWeightedAggregated);
    }

    @Override
    public boolean isWeightedAggregated() {
        return this.isWeightedAggregated;
    }
}
