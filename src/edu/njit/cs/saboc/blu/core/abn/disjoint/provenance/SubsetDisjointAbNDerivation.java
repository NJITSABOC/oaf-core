
package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a subset disjoint abstraction network
 * 
 * @author Chris O
 * @param <T>
 */
public class SubsetDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation<T> 
        implements SubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final Set<T> subset;
    
    public SubsetDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            Set<T> subset) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.subset = subset;
    }

    @Override
    public DisjointAbNDerivation<T> getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    public Set<T> getSubset() {
        return subset;
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        return getSuperAbNDerivation().getAbstractionNetwork().getSubsetDisjointAbN(subset);
    }

    @Override
    public String getDescription() {
        return String.format("%s (subset)", sourceDisjointAbNDerivation.getDescription());
    }

    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","SubsetDisjointAbNDerivation");       
        result.add(obj_class);

        
        //serialzie sourceDisjointAbNDerivation
        JSONObject obj_sourceDisjointAbNDerivation = new JSONObject();
        obj_sourceDisjointAbNDerivation.put("BaseDerivation", sourceDisjointAbNDerivation.serializeToJSON());   
        result.add(obj_sourceDisjointAbNDerivation);

        //serialize subset
        JSONObject obj_subset = new JSONObject();
        JSONArray arr = new JSONArray();
        subset.forEach(node ->{
            arr.add(node.getName());
        });        
        obj_subset.put("RootNodeNames", arr);
        result.add(obj_subset);
        
        return result;
        
    }
}
