package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.ExpandedSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaTaxonomyGenerator {
        
    public PAreaTaxonomy createAggregatePAreaTaxonomy(
            final PAreaTaxonomy sourceTaxonomy,
            final PAreaTaxonomyGenerator generator,
            final AggregateAbNGenerator<PArea, AggregatePArea> aggregateGenerator, 
            final int min) {
        
        if(min == 1) {
            return sourceTaxonomy;
        }
        
        Hierarchy<AggregatePArea> reducedPAreaHierarchy = 
                aggregateGenerator.createAggregateAbN(
                        new AggregatePAreaTaxonomyFactory(),
                        sourceTaxonomy.getPAreaHierarchy(), 
                        sourceTaxonomy.getSourceHierarchy(),
                        min);

        Hierarchy<PArea> pareaHierarchy = (Hierarchy<PArea>)(Hierarchy<?>)reducedPAreaHierarchy;

        PAreaTaxonomy aggregatedTaxonomy = generator.createTaxonomyFromPAreas(
                sourceTaxonomy.getPAreaTaxonomyFactory(), 
                pareaHierarchy, 
                sourceTaxonomy.getSourceHierarchy());
        
        
                       
        return new AggregatePAreaTaxonomy(
                sourceTaxonomy, 
                min, 
                aggregatedTaxonomy);
    }
    
    public ExpandedSubtaxonomy createExpandedSubtaxonomy(
            PAreaTaxonomy sourceAggregateTaxonomy,
            AggregatePArea aggregatePArea, 
            PAreaTaxonomyGenerator generator) {
        
        PAreaTaxonomy expandedSubtaxonomy = generator.createTaxonomyFromPAreas(
                sourceAggregateTaxonomy.getPAreaTaxonomyFactory(), 
                aggregatePArea.getAggregatedHierarchy(),
                sourceAggregateTaxonomy.getSourceHierarchy());
        
        return new ExpandedSubtaxonomy(
                sourceAggregateTaxonomy, 
                aggregatePArea, 
                expandedSubtaxonomy);
    }
}
