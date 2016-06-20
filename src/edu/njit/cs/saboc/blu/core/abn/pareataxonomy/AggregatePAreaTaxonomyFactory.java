package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.ontology.SingleRootedConceptHierarchy;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaTaxonomyFactory implements AggregateAbNFactory<PArea, AggregatePArea> {

    @Override
    public AggregatePArea createAggregateNode(NodeHierarchy<PArea> aggregatedNodes) {
        
        SingleRootedConceptHierarchy hierarchy = new SingleRootedConceptHierarchy(aggregatedNodes.getRoot().getRoot());
        
        aggregatedNodes.getNodesInHierarchy().forEach( (parea) -> {
            hierarchy.addAllHierarchicalRelationships(parea.getHierarchy());
        });
        
        return new AggregatePArea(hierarchy, aggregatedNodes.getRoot().getRelationships(), aggregatedNodes);
    }
}
