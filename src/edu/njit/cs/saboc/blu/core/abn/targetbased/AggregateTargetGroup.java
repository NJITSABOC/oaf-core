package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.NodeHierarchy;
import edu.njit.cs.saboc.blu.core.ontology.SingleRootedConceptHierarchy;

/**
 *
 * @author Chris O
 */
public class AggregateTargetGroup extends TargetGroup implements AggregateNode<TargetGroup>  {
    
    private final NodeHierarchy<TargetGroup> aggregatedGroups;
    
    public AggregateTargetGroup(SingleRootedConceptHierarchy conceptHierarchy, 
            IncomingRelationshipDetails relationships, 
            NodeHierarchy<TargetGroup> aggregatedGroups) {
        
        super(conceptHierarchy, relationships);
        
        this.aggregatedGroups = aggregatedGroups;
    }
    
    public NodeHierarchy<TargetGroup> getAggregatedHierarchy() {
        return aggregatedGroups;
    }
}
