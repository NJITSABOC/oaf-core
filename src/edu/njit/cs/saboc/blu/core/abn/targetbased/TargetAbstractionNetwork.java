
package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.DerivedTargetAbN;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class TargetAbstractionNetwork<T extends TargetGroup> extends AbstractionNetwork<T> 
    implements AggregateableAbstractionNetwork<TargetAbstractionNetwork<T>> {
    
    private boolean isAggregated = false;
    
    public TargetAbstractionNetwork(
            Hierarchy<T> groupHierarchy, 
            Hierarchy<Concept> sourceHierarchy,
            DerivedTargetAbN derivation) {
        
        super(groupHierarchy, sourceHierarchy, derivation);
    }
    
    @Override
    public DerivedTargetAbN getDerivation() {
        return (DerivedTargetAbN)super.getDerivation();
    }
    
    public Set<T> getTargetGroups() {
        return super.getNodes();
    }
    
    public Hierarchy<T> getTargetGroupHierarchy() {
        return super.getNodeHierarchy();
    }

    @Override
    public Set<ParentNodeDetails<T>> getParentNodeDetails(T group) {
        return AbstractionNetworkUtils.getSinglyRootedNodeParentNodeDetails(
                group, 
                this.getSourceHierarchy(),
                this.getTargetGroups());
    }

    @Override
    public boolean isAggregated() {
        return isAggregated;
    }
    
    public void setAggregated(boolean value) {
        this.isAggregated = value;
    }

    @Override
    public TargetAbstractionNetwork getAggregated(int smallestNode) {
        return AggregateTargetAbN.createAggregated(this, smallestNode);
    }
}
