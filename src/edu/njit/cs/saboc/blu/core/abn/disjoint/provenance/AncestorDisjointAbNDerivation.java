package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a given ancestor disjoint abstraction
 * network
 * 
 * @author Chris O
 */
public class AncestorDisjointAbNDerivation extends DisjointAbNDerivation
        implements RootedSubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final Concept disjointNodeRoot;
    
    public AncestorDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            Concept disjointNodeRoot) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.disjointNodeRoot = disjointNodeRoot;
    }

    @Override
    public Concept getSelectedRoot() {
        return disjointNodeRoot;
    }

    @Override
    public DisjointAbNDerivation getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        
        DisjointAbstractionNetwork sourceAbN = getSuperAbNDerivation().getAbstractionNetwork();
     
        Set<DisjointNode> nodes = sourceAbN.getNodesWith(disjointNodeRoot);
        
        return sourceAbN.getAncestorDisjointAbN(nodes.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("%s (ancestor subset)", sourceDisjointAbNDerivation.getDescription());
    }

    @Override
    public String getName() {
        return String.format("%s %s", disjointNodeRoot.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Ancestor %s", sourceDisjointAbNDerivation.getAbstractionNetworkTypeName());
    }
    
    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","AncestorDisjointAbNDerivation");       
        result.add(obj_class);
        
        //serialzie sourceDisjointAbNDerivation
        JSONObject obj_sourceDisjointAbNDerivation = new JSONObject();
        obj_sourceDisjointAbNDerivation.put("BaseDerivation", sourceDisjointAbNDerivation.serializeToJSON());   
        result.add(obj_sourceDisjointAbNDerivation);

        //serialize disjointNodeRoot
        JSONObject obj_disjointNodeRoot = new JSONObject();    
        obj_disjointNodeRoot.put("ConceptID", disjointNodeRoot.getIDAsString());
        result.add(obj_disjointNodeRoot);
        
        return result;       
    }     
}
