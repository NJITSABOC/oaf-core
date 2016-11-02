package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
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
                aggregateGenerator.createReducedAbN(
                        new AggregatePAreaTaxonomyFactory(),
                        sourceTaxonomy.getPAreaHierarchy(), 
                        sourceTaxonomy.getSourceHierarchy(),
                        min);
        
        Hierarchy<PArea> pareaHierarchy = (Hierarchy<PArea>)(Hierarchy<?>)reducedPAreaHierarchy;

        PAreaTaxonomy taxonomy = generator.createTaxonomyFromPAreas(
                sourceTaxonomy.getPAreaTaxonomyFactory(), 
                pareaHierarchy, 
                sourceTaxonomy.getSourceHierarchy());
        
        AggregatePAreaTaxonomy aggregateTaxonomy = new AggregatePAreaTaxonomy(
                sourceTaxonomy, 
                min, 
                taxonomy.getAreaTaxonomy(), 
                taxonomy.getPAreaHierarchy(),
                taxonomy.getSourceHierarchy());
        
        aggregateTaxonomy.setAggregated(true);
       
        return aggregateTaxonomy;
    }
    
    public ExpandedSubtaxonomy createExpandedSubtaxonomy(
            AggregatePAreaTaxonomy sourceAggregateTaxonomy,
            AggregatePArea aggregatePArea, 
            PAreaTaxonomyGenerator generator) {
        
        PAreaTaxonomy taxonomy = generator.createTaxonomyFromPAreas(
                sourceAggregateTaxonomy.getPAreaTaxonomyFactory(), 
                aggregatePArea.getAggregatedHierarchy(),
                sourceAggregateTaxonomy.getSourceHierarchy());
        
        ExpandedSubtaxonomy subtaxonomy = new ExpandedSubtaxonomy(
                sourceAggregateTaxonomy, 
                aggregatePArea, 
                taxonomy.getAreaTaxonomy(), 
                taxonomy.getPAreaHierarchy(), 
                taxonomy.getSourceHierarchy());
        
        return subtaxonomy;
    }
}
