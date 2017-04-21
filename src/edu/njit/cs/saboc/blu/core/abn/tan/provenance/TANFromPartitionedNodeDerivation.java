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
 * Stores the arguments needed to create a TAN from a partitioned node
 * in another abstraction network
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
    public String getName() {
        return String.format("%s %s", node.getName(), super.getAbstractionNetworkTypeName());
    }
    
    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","TANFromPartitionedNodeDerivation");       
        result.add(obj_class);

        //serialzie parentAbNDerivation
        JSONObject obj_parentAbNDerivation = new JSONObject();
        obj_parentAbNDerivation.put("ParentDerivation", parentAbNDerivation.serializeToJSON());
        result.add(obj_parentAbNDerivation);

        //serialize node
        JSONObject obj_node = new JSONObject();
        obj_node.put("NodeName", node.getName());
        result.add(obj_node);

        return result;
    } 
}
