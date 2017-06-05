package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;

import org.json.simple.JSONObject;


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
    private final boolean isWeightedAggregated;
    
    public AggregateDisjointAbNDerivation(
            DisjointAbNDerivation nonAggregateDerivation, 
            AggregatedProperty aggregatedProperty ) {
        
        super(nonAggregateDerivation);
        
        this.nonAggregateDerivation = nonAggregateDerivation;
        this.aggregateBound = aggregatedProperty.getBound();
        this.isWeightedAggregated = aggregatedProperty.getWeighted();
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
        return nonAggregateDerivation.getAbstractionNetwork().getAggregated(aggregateBound, isWeightedAggregated);
    }

    @Override
    public String getDescription() {
        if (isWeightedAggregated) {
            return String.format("%s (weighted aggregate: %d)", nonAggregateDerivation.getDescription(), aggregateBound);
        }
        return String.format("%s (aggregate: %d)", nonAggregateDerivation.getDescription(), aggregateBound);
    }

    @Override
    public String getName() {
        if (isWeightedAggregated) {
            return String.format("%s (Weighted Aggregated)", nonAggregateDerivation.getName());
        }
        return String.format("%s (Aggregated)", nonAggregateDerivation.getName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        if (isWeightedAggregated) {
            return String.format("Weighted Aggregate %s", nonAggregateDerivation.getAbstractionNetworkTypeName());
        }
        return String.format("Aggregate %s", nonAggregateDerivation.getAbstractionNetworkTypeName());
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "AggregateDisjointAbNDerivation");       
        result.put("BaseDerivation", nonAggregateDerivation.serializeToJSON());   
        result.put("Bound", aggregateBound);
        result.put("isWeightedAggregated", isWeightedAggregated);
        
        return result;
    }    

    @Override
    public boolean isWeightedAggregated() {
        return isWeightedAggregated;
    }

    @Override
    public AggregatedProperty getAggregatedProperty() {
        return new AggregatedProperty(aggregateBound, isWeightedAggregated);
    }
    
    
}
