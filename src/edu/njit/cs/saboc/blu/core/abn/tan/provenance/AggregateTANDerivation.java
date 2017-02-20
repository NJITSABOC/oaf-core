
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class AggregateTANDerivation extends ClusterTANDerivation 
        implements AggregateAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation nonAggregateSourceDerivation;
    private final int bound;
    
    public AggregateTANDerivation(ClusterTANDerivation nonAggregateSourceDerivation, int bound) {
        super(nonAggregateSourceDerivation);
        
        this.nonAggregateSourceDerivation = nonAggregateSourceDerivation;
        this.bound = bound;
    }
    
    public AggregateTANDerivation(AggregateTANDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getNonAggregateSourceDerivation(), deriveTaxonomy.getBound());
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
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","AggregateTANDerivation");       
        result.add(obj_class);
        
        //serialzie nonAggregateSourceDerivation
        JSONObject obj_nonAggregateSourceDerivation = new JSONObject();
        obj_nonAggregateSourceDerivation.put("BaseDerivation", nonAggregateSourceDerivation.serializeToJSON());   
        result.add(obj_nonAggregateSourceDerivation);

        //serialize bound
        JSONObject obj_bound = new JSONObject();
        obj_bound.put("Bound", bound);
        result.add(obj_bound);
       
        return result;
    }
    
}
