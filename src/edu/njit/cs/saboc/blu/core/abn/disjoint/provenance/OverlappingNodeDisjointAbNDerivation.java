package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a disjoint abstraction network based 
 * on the selection of an overlapping singly rooted node.
 * 
 * @author Chris O
 * @param <T>
 */
public class OverlappingNodeDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation
        implements SubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final T overlappingNode;
    
    public OverlappingNodeDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            T overlappingNode) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.overlappingNode = overlappingNode;
    }

    public T getOverlappingNode() {
        return overlappingNode;
    }
    
    @Override
    public DisjointAbNDerivation getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        DisjointAbstractionNetwork disjointAbN = getSuperAbNDerivation().getAbstractionNetwork();
        return disjointAbN.getOverlappingNodeDisjointAbN(overlappingNode);
    }

    @Override
    public String getDescription() {
        return String.format("%s (overlapping node)", sourceDisjointAbNDerivation.getDescription());
    }

    @Override
    public String getName() {
        return String.format("%s %s", overlappingNode.getName(), sourceDisjointAbNDerivation.getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Overlapping Node %s", sourceDisjointAbNDerivation.getAbstractionNetworkTypeName());
    }

    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName", "OverlappingNodeDisjointAbNDerivation");       
        result.add(obj_class);
        
        //serialzie sourceDisjointAbNDerivation
        JSONObject obj_sourceDisjointAbNDerivation = new JSONObject();
        obj_sourceDisjointAbNDerivation.put("BaseDerivation", sourceDisjointAbNDerivation.serializeToJSON());   
        result.add(obj_sourceDisjointAbNDerivation);

        //serialize overlappingNode
        JSONObject obj_overlappingNode = new JSONObject();    
        obj_overlappingNode.put("NodeName", overlappingNode.getName());
        result.add(obj_overlappingNode);
        
        return result;       
    }
}
