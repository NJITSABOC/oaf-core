
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an aggregate cluster TAN
 * 
 * @author Chris O
 */
public class AggregateTANDerivation extends ClusterTANDerivation 
        implements AggregateAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation nonAggregateSourceDerivation;
    private final int bound;
    private final boolean isWeightedAggregated;
    
    public AggregateTANDerivation(ClusterTANDerivation nonAggregateSourceDerivation, int bound, boolean isWeightedAggregated) {
        super(nonAggregateSourceDerivation);
        
        this.nonAggregateSourceDerivation = nonAggregateSourceDerivation;
        this.bound = bound;
        this.isWeightedAggregated = isWeightedAggregated;
    }
    
    public AggregateTANDerivation(AggregateTANDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getNonAggregateSourceDerivation(), deriveTaxonomy.getBound(), deriveTaxonomy.isWeightedAggregated());
    }
    
    @Override
    public ClusterTANDerivation getNonAggregateSourceDerivation() {
        return nonAggregateSourceDerivation;
    }
    
    @Override
    public int getBound() {
        return bound;
    }

    @Override
    public String getDescription() {
        return String.format("%s (aggregated: %d)", nonAggregateSourceDerivation.getDescription(), bound);
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        return getNonAggregateSourceDerivation().getAbstractionNetwork().getAggregated(bound);
    }
    
    @Override
    public String getName() {
        return String.format("%s (Aggregated)", nonAggregateSourceDerivation.getName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Aggregate Tribal Abstraction Network";
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "AggregateTANDerivation");       
        result.put("BaseDerivation", nonAggregateSourceDerivation.serializeToJSON());   
        result.put("Bound", bound);
        result.put("isWeightedAggregated", isWeightedAggregated);
        
        return result;
    }

    @Override
    public boolean isWeightedAggregated() {
        return isWeightedAggregated;
    }
}
