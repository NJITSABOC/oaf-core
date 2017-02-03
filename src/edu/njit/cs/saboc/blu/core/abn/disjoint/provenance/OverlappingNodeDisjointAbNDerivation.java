package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class OverlappingNodeDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation<T> 
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
    public DisjointAbNDerivation<T> getSuperAbNDerivation() {
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
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        result.add("OverlappingNodeDisjointAbNDerivation");
        
        //serialzie sourceDisjointAbNDerivation
        JSONObject obj_sourceDisjointAbNDerivation = new JSONObject();
        obj_sourceDisjointAbNDerivation.put("BaseDerivation", sourceDisjointAbNDerivation.serializeToJSON());   
        result.add(obj_sourceDisjointAbNDerivation);

        //serialize overlappingNode
        JSONObject obj_overlappingNode = new JSONObject();    
        obj_overlappingNode.put("RootID", overlappingNode.getRoot().getIDAsString());
        result.add(obj_overlappingNode);
        
        return result;       
    }    
    
}