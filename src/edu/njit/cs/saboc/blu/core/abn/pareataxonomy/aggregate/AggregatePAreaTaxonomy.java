package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.ExpandedSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RootSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregatePAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * A partial-area taxonomy where all of the partial-areas smaller than a given 
 * bound are summarized by aggregate partial-areas
 * 
 * @author Chris O
 */
public class AggregatePAreaTaxonomy extends PAreaTaxonomy<AggregatePArea> 
        implements AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy> {
           
    public static final PAreaTaxonomy generateAggregatePAreaTaxonomy(PAreaTaxonomy nonAggregateTaxonomy, int bound, boolean weightedAggregated) {
        AggregatePAreaTaxonomyGenerator generator = new AggregatePAreaTaxonomyGenerator();
        
        return generator.createAggregatePAreaTaxonomy(
                nonAggregateTaxonomy, 
                new PAreaTaxonomyGenerator(),
                new AggregateAbNGenerator<>(),
                bound,
                weightedAggregated);
    }
    
    
    public static final PAreaTaxonomy generateAggregateRootSubtaxonomy(
            PAreaTaxonomy nonAggregateTaxonomy,
            PAreaTaxonomy superAggregateTaxonomy,
            AggregatePArea selectedRoot) {
        
        RootSubtaxonomy nonAggregateRootSubtaxonomy = (RootSubtaxonomy)nonAggregateTaxonomy.createRootSubtaxonomy(
                selectedRoot.getAggregatedHierarchy().getRoot());
        
        int aggregateBound = ((AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy>)superAggregateTaxonomy).getAggregateBound();
        boolean weightedAggregated = ((AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy>)superAggregateTaxonomy).getAggregatedProperty().getWeighted();
        
        PAreaTaxonomy aggregateRootSubtaxonomy = nonAggregateRootSubtaxonomy.getAggregated(aggregateBound);
        
        return new AggregateRootSubtaxonomy(
                superAggregateTaxonomy,
                aggregateBound,
                nonAggregateRootSubtaxonomy, 
                aggregateRootSubtaxonomy,
                weightedAggregated
        );
    }
    
    public static final PAreaTaxonomy generateAggregateAncestorSubtaxonomy(
            PAreaTaxonomy nonAggregateTaxonomy,
            PAreaTaxonomy superAggregateTaxonomy,
            AggregatePArea selectedRoot) {
        
        Hierarchy<PArea> actualPAreaHierarchy = AbstractionNetworkUtils.getDeaggregatedAncestorHierarchy(
                nonAggregateTaxonomy.getPAreaHierarchy(), 
                superAggregateTaxonomy.getPAreaHierarchy().getAncestorHierarchy(selectedRoot));
        
        Hierarchy<Concept> sourceHierarchy = AbstractionNetworkUtils.getConceptHierarchy(
                actualPAreaHierarchy, 
                nonAggregateTaxonomy.getSourceHierarchy());
        
        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        PAreaTaxonomy nonAggregatedAncestorSubtaxonomy = generator.createTaxonomyFromPAreas(
                nonAggregateTaxonomy.getPAreaTaxonomyFactory(), 
                actualPAreaHierarchy, 
                sourceHierarchy);

        int aggregateBound = ((AggregateAbstractionNetwork)superAggregateTaxonomy).getAggregateBound();
        boolean weightedAggregate = ((AggregateAbstractionNetwork)superAggregateTaxonomy).getAggregatedProperty().getWeighted();
        
        // Convert to ancestor subhierarchy
        AncestorSubtaxonomy subtaxonomy = new AncestorSubtaxonomy(
                nonAggregateTaxonomy,
                selectedRoot.getAggregatedHierarchy().getRoot(),
                nonAggregatedAncestorSubtaxonomy); 
        
        PAreaTaxonomy aggregateAncestorSubtaxonomy = subtaxonomy.getAggregated(aggregateBound);
        
        return new AggregateAncestorSubtaxonomy(
                superAggregateTaxonomy,
                aggregateBound,
                selectedRoot,
                subtaxonomy, 
                aggregateAncestorSubtaxonomy,
                weightedAggregate
        );
    }
    
    public static final ExpandedSubtaxonomy generateExpandedSubtaxonomy(
            PAreaTaxonomy aggregateTaxonomy, 
            AggregatePArea parea) {
        
         AggregatePAreaTaxonomyGenerator generator = new AggregatePAreaTaxonomyGenerator();
        
        return generator.createExpandedSubtaxonomy(aggregateTaxonomy, parea, new PAreaTaxonomyGenerator());
    }
    
    private final PAreaTaxonomy nonAggregateBaseTaxonomy;
    
    private final int minBound;
    
    private final boolean weightedAggregated;
    
    public AggregatePAreaTaxonomy(
            PAreaTaxonomy nonAggregateBaseTaxonomy,
            int minBound,
            PAreaTaxonomy aggregatedPAreaTaxonomy,
            boolean weightedAggregated) {
    
        super(aggregatedPAreaTaxonomy.getAreaTaxonomy(), 
                aggregatedPAreaTaxonomy.getPAreaHierarchy(), 
                nonAggregateBaseTaxonomy.getSourceHierarchy(), 
                new AggregatePAreaTaxonomyDerivation(nonAggregateBaseTaxonomy.getDerivation(), minBound));
        
        this.nonAggregateBaseTaxonomy = nonAggregateBaseTaxonomy;
        this.minBound = minBound;
        this.weightedAggregated = weightedAggregated;
    }
    
    @Override
    public AggregatePAreaTaxonomyDerivation getDerivation() {
        return (AggregatePAreaTaxonomyDerivation)super.getDerivation();
    }
    
    @Override
    public PAreaTaxonomy getNonAggregateSourceAbN() {
        return nonAggregateBaseTaxonomy;
    }
    
    @Override
    public int getAggregateBound() {
        return minBound;
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
        return AggregatePAreaTaxonomy.generateAggregateRootSubtaxonomy(this.getNonAggregateSourceAbN(), this, root);
    }

    @Override
    public PAreaTaxonomy createAncestorSubtaxonomy(AggregatePArea source) {
        return AggregatePAreaTaxonomy.generateAggregateAncestorSubtaxonomy(this.getNonAggregateSourceAbN(), this, source);
    }
    
    @Override
    public AggregatedProperty getAggregatedProperty(){
        return new AggregatedProperty(minBound, weightedAggregated);
    }
}