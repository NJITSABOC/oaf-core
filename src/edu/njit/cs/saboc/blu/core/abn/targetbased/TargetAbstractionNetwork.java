
package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.provenance.CachedAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * An abstraction network that summarizes subhierarchies of concepts that are 
 * targets of a certain kind of relationship
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
            TargetAbNDerivation derivation) {
        
        super(groupHierarchy, sourceHierarchy, derivation);
    }
    
    public TargetAbstractionNetwork(TargetAbstractionNetwork targetAbN) {
        
        this(targetAbN.getNodeHierarchy(), 
                targetAbN.getSourceHierarchy(), 
                targetAbN.getDerivation());
    }
    
    @Override
    public TargetAbNDerivation getDerivation() {
        return (TargetAbNDerivation)super.getDerivation();
    }
    
    @Override
    public CachedAbNDerivation<TargetAbstractionNetwork> getCachedDerivation() {
        return super.getCachedDerivation();
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
