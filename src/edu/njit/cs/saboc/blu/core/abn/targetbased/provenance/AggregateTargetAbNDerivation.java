package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an aggregate target abstraction network
 * 
 * @author Chris O
 */
public class AggregateTargetAbNDerivation extends TargetAbNDerivation 
    implements AggregateAbNDerivation<TargetAbNDerivation> {
    
    private final TargetAbNDerivation nonAggregateSource;
    private final int bound;
    private final boolean isWeightedAggregated;
    
    public AggregateTargetAbNDerivation(TargetAbNDerivation nonAggregateSource, int bound, boolean isWeightedAggregated) {
        super(nonAggregateSource);
        
        this.nonAggregateSource = nonAggregateSource;
        this.bound = bound;
        this.isWeightedAggregated = isWeightedAggregated;
    }

    @Override
    public int getBound() {
        return bound;
    }

    @Override
    public TargetAbNDerivation getNonAggregateSourceDerivation() {
        return nonAggregateSource;
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork() {
        TargetAbstractionNetwork targetAbN = this.getNonAggregateSourceDerivation().getAbstractionNetwork();
        
        return targetAbN.getAggregated(bound);
    }

    @Override
    public String getDescription() {
        return String.format("%s (aggregated: %d)", super.getDescription(), bound);
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName","AggregateTargetAbNDerivation");       
        result.put("BaseDerivation", nonAggregateSource.serializeToJSON());   
        result.put("Bound", bound);
        result.put("isWeightedAggregated", isWeightedAggregated);

        return result;
    }

    @Override
    public boolean isWeightedAggregated() {
        return isWeightedAggregated;
    }
}
