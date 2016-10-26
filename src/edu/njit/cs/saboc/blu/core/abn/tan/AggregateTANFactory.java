package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AggregateTANFactory implements AggregateAbNFactory<Cluster, AggregateCluster> {

    @Override
    public AggregateCluster createAggregateNode(Hierarchy<Cluster> aggregatedNodes) {
        
        Hierarchy<Concept> hierarchy = new Hierarchy<>(aggregatedNodes.getRoot().getRoot());
        
        aggregatedNodes.getNodes().forEach( (parea) -> {
            hierarchy.addAllHierarchicalRelationships(parea.getHierarchy());
        });
        
        return new AggregateCluster(hierarchy, aggregatedNodes.getRoot().getPatriarchs(), aggregatedNodes);
    }
}
