package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateTargetGroup extends TargetGroup implements AggregateNode<TargetGroup>  {
    
    private final Hierarchy<TargetGroup> aggregatedGroups;
    
    public AggregateTargetGroup(Hierarchy<Concept> conceptHierarchy, 
            IncomingRelationshipDetails relationships, 
            Hierarchy<TargetGroup> aggregatedGroups) {
        
        super(conceptHierarchy, relationships);
        
        this.aggregatedGroups = aggregatedGroups;
    }
    
    public Hierarchy<TargetGroup> getAggregatedHierarchy() {
        return aggregatedGroups;
    }
    
    public Set<TargetGroup> getAggregatedNodes() {
        Set<TargetGroup> nodes = new HashSet<>(aggregatedGroups.getNodesInHierarchy());
        nodes.remove(aggregatedGroups.getRoot());
        
        return nodes;
    }
}
