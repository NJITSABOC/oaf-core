package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaTaxonomyFactory implements AggregateAbNFactory<PArea, AggregatePArea> {

    @Override
    public AggregatePArea createAggregateNode(Hierarchy<PArea> aggregatedNodes) {
        
        Hierarchy<Concept> hierarchy = new Hierarchy<>(aggregatedNodes.getRoot().getRoot());
        
        aggregatedNodes.getNodes().forEach( (parea) -> {
            hierarchy.addAllHierarchicalRelationships(parea.getHierarchy());
        });
        
        return new AggregatePArea(hierarchy, aggregatedNodes.getRoot().getRelationships(), aggregatedNodes);
    }
}
