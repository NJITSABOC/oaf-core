package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class TANFromPartitionedNodeDerivation<
        T extends AbNDerivation, 
        V extends PartitionedNode> extends ClusterTANDerivation {
    
    private final T parentAbNDerivation;
    private final V node;
    
    public TANFromPartitionedNodeDerivation(
            T parentAbNDerivation, 
            TANFactory factory,
            V node) {
        
        super(parentAbNDerivation.getSourceOntology(), factory);
        
        this.parentAbNDerivation = parentAbNDerivation;
        this.node = node;
    }
    
    public T getParentAbNDerivation() {
        return parentAbNDerivation;
    }
    
    public V getSourcePartitionedNode() {
        return node;
    }

    @Override
    public String getDescription() {
        return String.format("Derived TAN from %s", node.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        return generator.deriveTANFromMultiRootedHierarchy(
                node.getHierarchy(),
                super.getFactory());
    }
    
    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        result.add("TANFromPartitionedNodeDerivation");

        //serialzie parentAbNDerivation
        JSONObject obj_parentAbNDerivation = new JSONObject();
        obj_parentAbNDerivation.put("ParentDerivation", parentAbNDerivation.serializeToJSON());
        result.add(obj_parentAbNDerivation);

        //serialize node
        JSONObject obj_node = new JSONObject();
        JSONArray arr = new JSONArray();
        Set<Concept> set = node.getRoots();
        set.forEach(root -> {
            arr.add(root.getIDAsString());
        });
        obj_node.put("RootIDs", arr);
        result.add(obj_node);

        return result;
    }  
    
}
