package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a TAN from a singly rooted node 
 * thats in a different abstraction network
 * 
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class TANFromSinglyRootedNodeDerivation <
        T extends AbNDerivation, 
        V extends SinglyRootedNode> extends ClusterTANDerivation {
    
    private final T parentAbNDerivation;
    private final Concept nodeRoot;
    
    public TANFromSinglyRootedNodeDerivation(
            T parentAbNDerivation, 
            TANFactory factory,
            Concept nodeRoot) {
        
        super(parentAbNDerivation.getSourceOntology(), 
                factory);
        
        this.parentAbNDerivation = parentAbNDerivation;
        this.nodeRoot = nodeRoot;
    }
    
    public T getParentAbNDerivation() {
        return parentAbNDerivation;
    }
    
    public Concept getRootConcept() {
        return nodeRoot;
    }

    @Override
    public String getDescription() {
        return String.format("Derived TAN from %s", nodeRoot.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        AbstractionNetwork<V> sourceAbN = parentAbNDerivation.getAbstractionNetwork();
        
        Set<V> nodes = sourceAbN.getNodesWith(nodeRoot);
        
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        return generator.createTANFromSinglyRootedNode(sourceAbN, nodes.iterator().next(), super.getFactory());
    }

    @Override
    public String getName() {
        return String.format("%s %s", nodeRoot.getName(), super.getAbstractionNetworkTypeName());
    }

    public JSONArray serializeToJSON() {        
        JSONArray result = new JSONArray();
        
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","TANFromSinglyRootedNodeDerivation");       
        result.add(obj_class);
        
        //serialzie parentAbNDerivation
        JSONObject obj_parentAbNDerivation = new JSONObject();
        obj_parentAbNDerivation.put("ParentDerivation", parentAbNDerivation.serializeToJSON());   
        result.add(obj_parentAbNDerivation);
        
        //serialzie nodeRoot
        JSONObject obj_nodeRoot = new JSONObject();
        obj_nodeRoot.put("RootID", nodeRoot.getIDAsString());   
        result.add(obj_nodeRoot);
        
        return result;
    }   
}
