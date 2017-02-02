
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaTaxonomyDerivation extends PAreaTaxonomyDerivation 
        implements AggregateAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final PAreaTaxonomyDerivation nonAggregateSourceDerivation;
    private final int bound;
    
    public AggregatePAreaTaxonomyDerivation(PAreaTaxonomyDerivation nonAggregateSourceDerivation, int bound) {
        super(nonAggregateSourceDerivation);
        
        this.nonAggregateSourceDerivation = nonAggregateSourceDerivation;
        this.bound = bound;
    }
    
    public AggregatePAreaTaxonomyDerivation(AggregatePAreaTaxonomyDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getNonAggregateSourceDerivation(), deriveTaxonomy.getBound());
    }
    
    @Override
    public PAreaTaxonomyDerivation getNonAggregateSourceDerivation() {
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
    public PAreaTaxonomy getAbstractionNetwork() {
        return getNonAggregateSourceDerivation().getAbstractionNetwork().getAggregated(bound);
    }
    
    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        result.add("AggregatePAreaTaxonomyDerivation");
        
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
