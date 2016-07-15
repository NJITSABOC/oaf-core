package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateTargetAbNFactory implements AggregateAbNFactory<TargetGroup, AggregateTargetGroup> {

    @Override
    public AggregateTargetGroup createAggregateNode(Hierarchy<TargetGroup> aggregatedNodes) {
        
        Hierarchy<Concept> hierarchy = new Hierarchy<>(aggregatedNodes.getRoot().getRoot());
        
        Set<RelationshipTriple> allRelationshipTriples = new HashSet<>();
        
        aggregatedNodes.getNodes().forEach((group) -> {
            hierarchy.addAllHierarchicalRelationships(group.getHierarchy());
            
            allRelationshipTriples.addAll(group.getIncomingRelationshipDetails().getAllRelationships());
        });
        
        return new AggregateTargetGroup(hierarchy, new IncomingRelationshipDetails(allRelationshipTriples), aggregatedNodes);
    }
}