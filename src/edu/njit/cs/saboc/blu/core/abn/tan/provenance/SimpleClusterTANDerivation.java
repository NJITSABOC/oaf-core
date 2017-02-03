
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class SimpleClusterTANDerivation extends ClusterTANDerivation {
    
    private final Set<Concept> patriarchs;
    
    public SimpleClusterTANDerivation(Set<Concept> patriarchs, Ontology sourceOntology, TANFactory factory) {
        super(sourceOntology, factory);
        
        this.patriarchs = patriarchs;
    }
    
    public SimpleClusterTANDerivation(SimpleClusterTANDerivation base) {
        this(base.getPatriarchs(), base.getSourceOntology(), base.getFactory());
    }
            
    public Set<Concept> getPatriarchs() {
        return patriarchs;
    }
    
    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        ClusterTribalAbstractionNetwork tan = generator.deriveTANFromMultiRootedHierarchy(
                super.getSourceOntology().getConceptHierarchy().getSubhierarchyRootedAt(patriarchs), super.getFactory());

        return tan;
    }

    @Override
    public String getDescription() {
        return String.format("Derived tribal abstraction network");
    }
    
    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        result.add("SimpleClusterTANDerivation");

        //serialize patriarchs
        JSONObject obj_patriarchs = new JSONObject();
        JSONArray arr = new JSONArray();
        patriarchs.forEach(node ->{
            arr.add(node.getIDAsString());
        });        
        obj_patriarchs.put("ConceptIDs", arr);
        result.add(obj_patriarchs);
        
        return result;
        
    }    
    
}
