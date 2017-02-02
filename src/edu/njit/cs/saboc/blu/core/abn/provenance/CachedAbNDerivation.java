
package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class CachedAbNDerivation<T extends AbstractionNetwork> extends AbNDerivation<T> {
    private final T abn;
    
    public CachedAbNDerivation(T abn) {
        super(abn.getDerivation().getSourceOntology());
        
        this.abn = abn;
    }

    @Override
    public String getDescription() {
        return String.format("%s (cached)", abn.getDerivation().getDescription());
    }

    @Override
    public T getAbstractionNetwork() {
        return abn;
    }

    @Override
    public JSONArray serializeToJSON() {        
        JSONArray result = new JSONArray();
        return result;
    }
    
    
}
