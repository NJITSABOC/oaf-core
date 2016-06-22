package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.ontology.ConceptHierarchy;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateTargetGroup extends TargetGroup implements AggregateNode<TargetGroup>  {
    
    private final NodeHierarchy<TargetGroup> aggregatedGroups;
    
    public AggregateTargetGroup(ConceptHierarchy conceptHierarchy, 
            IncomingRelationshipDetails relationships, 
            NodeHierarchy<TargetGroup> aggregatedGroups) {
        
        super(conceptHierarchy, relationships);
        
        this.aggregatedGroups = aggregatedGroups;
    }
    
    public NodeHierarchy<TargetGroup> getAggregatedHierarchy() {
        return aggregatedGroups;
    }
    
    public Set<TargetGroup> getAggregatedNodes() {
        Set<TargetGroup> nodes = new HashSet<>(aggregatedGroups.getNodesInHierarchy());
        nodes.remove(aggregatedGroups.getRoot());
        
        return nodes;
    }
}
