package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateTargetAbNFactory implements AggregateAbNFactory<TargetGroup, AggregateTargetGroup> {

    @Override
    public AggregateTargetGroup createAggregateNode(NodeHierarchy<TargetGroup> aggregatedNodes) {
        
        ConceptHierarchy hierarchy = new ConceptHierarchy(aggregatedNodes.getRoot().getRoot());
        
        Set<RelationshipTriple> allRelationshipTriples = new HashSet<>();
        
        aggregatedNodes.getNodesInHierarchy().forEach((group) -> {
            hierarchy.addAllHierarchicalRelationships(group.getHierarchy());
            
            allRelationshipTriples.addAll(group.getIncomingRelationshipDetails().getAllRelationships());
        });
        
        return new AggregateTargetGroup(hierarchy, new IncomingRelationshipDetails(allRelationshipTriples), aggregatedNodes);
    }
}