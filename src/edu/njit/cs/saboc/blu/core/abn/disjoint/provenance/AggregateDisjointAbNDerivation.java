package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import static javafx.scene.input.KeyCode.T;

/**
 * Stores the arguments needed to create an aggregate disjoint abstraction 
 * network
 * 
 * @author Chris O
 */
public class AggregateDisjointAbNDerivation extends DisjointAbNDerivation
        implements AggregateAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation nonAggregateDerivation;
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

    @Override
    public String getName() {
        return String.format("%s (Aggregated)", nonAggregateDerivation.getName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Aggregate %s", nonAggregateDerivation.getAbstractionNetworkTypeName());
    }
}
