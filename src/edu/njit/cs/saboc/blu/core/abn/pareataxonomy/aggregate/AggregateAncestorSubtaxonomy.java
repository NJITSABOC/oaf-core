package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregateAncestorSubtaxonomyDerivation;

/**
 * An aggregate partial-area taxonomy that consists of a selected aggregate
 * partial-area and all of its aggregate partial-area ancestors
 * 
 * @author Chris O
 */
public class AggregateAncestorSubtaxonomy extends AncestorSubtaxonomy<AggregatePArea> 
        implements AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy> {
    
    private final AncestorSubtaxonomy nonAggregatedRootSubtaxonomy;
    private final int aggregateBound;
    private final boolean isWeightedAggregated;
    
    public AggregateAncestorSubtaxonomy(
            PAreaTaxonomy aggregatedSuperAbN, 
            int aggregateBound,
            AggregatePArea selectedRoot,
            AncestorSubtaxonomy nonAggregatedRootSubtaxonomy,
            PAreaTaxonomy subtaxonomy,
            boolean isWeightedAggregated) {
        
        super(aggregatedSuperAbN, 
                selectedRoot, 
                subtaxonomy, 
                new AggregateAncestorSubtaxonomyDerivation(
                        aggregatedSuperAbN.getDerivation(), 
                        aggregateBound, 
                        selectedRoot.getRoot(),
                        isWeightedAggregated));
        
        this.nonAggregatedRootSubtaxonomy = nonAggregatedRootSubtaxonomy;
        this.aggregateBound = aggregateBound;
        this.isWeightedAggregated = isWeightedAggregated;
    }
    
    public AggregateAncestorSubtaxonomy(AggregateAncestorSubtaxonomy subtaxonomy) {
        this(subtaxonomy.getSuperAbN(), 
                subtaxonomy.getAggregateBound(), 
                subtaxonomy.getSelectedRoot(), 
                subtaxonomy.getNonAggregateSourceAbN(), 
                subtaxonomy,
                subtaxonomy.getAggregatedProperty().getWeighted()
                );
    }

    @Override
    public AncestorSubtaxonomy getNonAggregateSourceAbN() {
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
    public PAreaTaxonomy getWeightedAggregated(int aggregateBound, boolean isWeightedAggregated) {
        return AggregatePAreaTaxonomy.generateAggregatePAreaTaxonomy(this.getNonAggregateSourceAbN(), aggregateBound, isWeightedAggregated);
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
}
