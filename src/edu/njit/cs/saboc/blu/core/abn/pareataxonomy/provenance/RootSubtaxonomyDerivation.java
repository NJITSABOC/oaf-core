package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class RootSubtaxonomyDerivation extends PAreaTaxonomyDerivation 
        implements RootedSubAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final PAreaTaxonomyDerivation base;
    private final Concept pareaRootConcept;
    
    public RootSubtaxonomyDerivation(PAreaTaxonomyDerivation base, Concept pareaRootConcept) {
        super(base);
        
        this.base = base;
        this.pareaRootConcept = pareaRootConcept;
    }
    
    public RootSubtaxonomyDerivation(RootSubtaxonomyDerivation base) {
        this(base.getSuperAbNDerivation(), base.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return pareaRootConcept;
    }

    @Override
    public PAreaTaxonomyDerivation getSuperAbNDerivation() {
        return base;
    }
    
    @Override
    public String getDescription() {
        return String.format("Derived root subtaxonomy (root: %s)", pareaRootConcept.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomy taxonomy = base.getAbstractionNetwork();
        
        Set<PArea> pareas = taxonomy.getNodesWith(pareaRootConcept);
        
        return taxonomy.createRootSubtaxonomy(pareas.iterator().next());
    }
    
    
    @Override
    public JSONArray serializeToJSON() {        
        JSONArray result = new JSONArray();

        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","RootSubtaxonomyDerivation");       
        result.add(obj_class);
        
        //serialzie base
        JSONObject obj_base = new JSONObject();
        obj_base.put("BaseDerivation", base.serializeToJSON());   
        result.add(obj_base);
        
        //serialzie pareaRootConcept
        JSONObject obj_pareaRootConcept = new JSONObject();
        obj_pareaRootConcept.put("ConceptID", pareaRootConcept.getIDAsString());   
        result.add(obj_pareaRootConcept);
        
        return result;
    }
    
}
