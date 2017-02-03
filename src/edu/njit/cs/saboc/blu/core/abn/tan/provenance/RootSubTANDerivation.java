package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class RootSubTANDerivation extends ClusterTANDerivation 
        implements RootedSubAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation base;
    private final Concept clusterRootConcept;
    
    public RootSubTANDerivation(ClusterTANDerivation base, Concept clusterRootConcept) {
        super(base);
        
        this.base = base;
        this.clusterRootConcept = clusterRootConcept;
    }
    
    public RootSubTANDerivation(RootSubTANDerivation base) {
        this(base.getSuperAbNDerivation(), base.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return clusterRootConcept;
    }

    @Override
    public ClusterTANDerivation getSuperAbNDerivation() {
        return base;
    }
    
    @Override
    public String getDescription() {
        return String.format("Derived Root Sub TAN (Cluster: %s)", clusterRootConcept.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        ClusterTribalAbstractionNetwork tan = base.getAbstractionNetwork();
        
        Set<Cluster> clusters = tan.getNodesWith(clusterRootConcept);
        
        return tan.createRootSubTAN(clusters.iterator().next());
    }
    
    @Override
    public JSONArray serializeToJSON() {        
        JSONArray result = new JSONArray();
        result.add("RootSubTANDerivation");
        
        //serialzie base
        JSONObject obj_base = new JSONObject();
        obj_base.put("BaseDerivation", base.serializeToJSON());   
        result.add(obj_base);
        
        //serialzie clusterRootConcept
        JSONObject obj_clusterRootConcept = new JSONObject();
        obj_clusterRootConcept.put("ConceptID", clusterRootConcept.getIDAsString());   
        result.add(obj_clusterRootConcept);
        
        return result;
    }    
}
