package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;

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
        
        NodeHierarchy<AggregatePArea> reducedPAreaHierarchy = 
                aggregateGenerator.createReducedAbN(
                        new AggregatePAreaTaxonomyFactory(),
                        sourceTaxonomy.getPAreaHierarchy(), 
                        min);

        PAreaTaxonomy reducedTaxonomy = generator.createTaxonomyFromPAreas((NodeHierarchy<PArea>)(NodeHierarchy<?>)reducedPAreaHierarchy);
       
        return reducedTaxonomy;
    }
    
    public PAreaTaxonomy createExpandedSubtaxonomy(
            AggregatePArea aggregatePArea, 
            PAreaTaxonomyGenerator generator) {
        
        return generator.createTaxonomyFromPAreas(aggregatePArea.getAggregatedHierarchy());
    }
}
