package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AggregateTargetAbN<T extends TargetGroup> extends TargetAbstractionNetwork<T> {
    
    private final TargetAbstractionNetwork sourceTargetAbN;
    
    private final int minBound;

    public AggregateTargetAbN(TargetAbstractionNetwork sourceTargetAbN,
            int minBound,
            Hierarchy<T> groupHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        super(groupHierarchy, sourceHierarchy);
        
        this.sourceTargetAbN = sourceTargetAbN;
        this.minBound = minBound;
    }

    public TargetAbstractionNetwork getTargetAbN() {
        return sourceTargetAbN;
    }
    
    public int getMinBound() {
        return minBound;
    }
}
