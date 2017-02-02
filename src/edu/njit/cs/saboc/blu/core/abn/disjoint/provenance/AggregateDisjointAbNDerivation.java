package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
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
    
    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        result.add("AggregateDisjointAbNDerivation");
        
        //serialzie nonAggregateDerivation
        JSONObject obj_nonAggregateDerivation = new JSONObject();
        obj_nonAggregateDerivation.put("BaseDerivation", nonAggregateDerivation.serializeToJSON());   
        result.add(obj_nonAggregateDerivation);

        //serialize aggregateBound
        JSONObject obj_aggregateBound = new JSONObject();
        obj_aggregateBound.put("Bound", aggregateBound);
        result.add(obj_aggregateBound);
        
        return result;
    }    
}
