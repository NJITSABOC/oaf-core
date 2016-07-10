
package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class TargetAbstractionNetwork extends AbstractionNetwork<TargetGroup> 
    implements AggregateableAbstractionNetwork<TargetAbstractionNetwork> {
    
    private boolean isAggregated = false;
    
    public TargetAbstractionNetwork(
            Hierarchy<TargetGroup> groupHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {
        
        super(groupHierarchy, sourceHierarchy);
    }
    
    public Set<TargetGroup> getTargetGroups() {
        return super.getNodes();
    }
    
    public Hierarchy<TargetGroup> getTargetGroupHierarchy() {
        return super.getNodeHierarchy();
    }

    @Override
    public Set<ParentNodeDetails> getParentNodeDetails(TargetGroup group) {
                return AbstractionNetworkUtils.getSinglyRootedNodeParentNodeDetails(
                group, 
                this.getSourceHierarchy(),
                (Set<SinglyRootedNode>)(Set<?>)this.getTargetGroups());
    }

    @Override
    public boolean isAggregated() {
        return isAggregated;
    }

    @Override
    public TargetAbstractionNetwork getAggregated(int smallestNode) {
        AggregateTargetAbNGenerator generator = new AggregateTargetAbNGenerator();
        
        TargetAbstractionNetwork aggregateTAN = 
                generator.createAggregateTargetAbN(this, 
                new TargetAbstractionNetworkGenerator(), 
                new AggregateAbNGenerator<>(), 
                smallestNode);
        
        aggregateTAN.isAggregated = true;
        
        return aggregateTAN;
    }
}
