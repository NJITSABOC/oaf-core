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
    
    public AggregateTargetAbNDerivation(TargetAbNDerivation nonAggregateSource, int bound) {
        super(nonAggregateSource);
        
        this.nonAggregateSource = nonAggregateSource;
        this.bound = bound;
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
    public JSONArray serializeToJSON() {
 
        JSONArray result = new JSONArray();
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","AggregateTargetAbNDerivation");       
        result.add(obj_class);

        
        //serialzie nonAggregateSource
        JSONObject obj_nonAggregateSource = new JSONObject();
        obj_nonAggregateSource.put("BaseDerivation", nonAggregateSource.serializeToJSON());   
        result.add(obj_nonAggregateSource);

        //serialize bound
        JSONObject obj_bound = new JSONObject();
        obj_bound.put("Bound", bound);
        result.add(obj_bound);
        
        return result;
        
    }
}
