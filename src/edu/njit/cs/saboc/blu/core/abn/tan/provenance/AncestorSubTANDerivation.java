
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an ancestor sub TAN
 * 
 * @author Chris O
 */
public class AncestorSubTANDerivation extends ClusterTANDerivation 
    implements RootedSubAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation base;
    private final Concept clusterRootConcept;
    
    public AncestorSubTANDerivation(ClusterTANDerivation base, Concept clusterRootConcept) {
        super(base);
        
        this.base = base;
        this.clusterRootConcept = clusterRootConcept;
    }
    
    public AncestorSubTANDerivation(AncestorSubTANDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), deriveTaxonomy.getSelectedRoot());
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
        return String.format("Derived ancestor Sub TAN (Cluster: %s)", clusterRootConcept.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        ClusterTribalAbstractionNetwork tan = base.getAbstractionNetwork();
        
        Set<Cluster> clusters = tan.getNodesWith(clusterRootConcept);
        
        return tan.createAncestorTAN(clusters.iterator().next());
    }
    
    @Override
    public JSONArray serializeToJSON() {        
        JSONArray result = new JSONArray();

        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","AncestorSubTANDerivation");       
        result.add(obj_class);
        
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
