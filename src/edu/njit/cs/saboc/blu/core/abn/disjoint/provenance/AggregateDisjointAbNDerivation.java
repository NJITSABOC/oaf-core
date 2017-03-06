package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;

/**
 * Stores the arguments needed to create an aggregate disjoint abstraction 
 * network
 * 
 * @author Chris O
 * @param <T>
 */
public class AggregateDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation<T> 
        implements AggregateAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation<T> nonAggregateDerivation;
    private final int aggregateBound;
    
    public AggregateDisjointAbNDerivation(
            DisjointAbNDerivation nonAggregateDerivation, 
            int aggregateBound) {
        
        super(nonAggregateDerivation);
        
        this.nonAggregateDerivation = nonAggregateDerivation;
        this.aggregateBound = aggregateBound;
    }

    @Override
    public int getBound() {
        return aggregateBound;
    }

    @Override
    public DisjointAbNDerivation getNonAggregateSourceDerivation() {
        return nonAggregateDerivation;
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        return nonAggregateDerivation.getAbstractionNetwork().getAggregated(aggregateBound);
    }

    @Override
    public String getDescription() {
        return String.format("%s (aggregate: %d)", nonAggregateDerivation.getDescription(), aggregateBound);
    }
}
