package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class SimplePAreaTaxonomyDerivation extends PAreaTaxonomyDerivation {

    private final Concept root;

    public SimplePAreaTaxonomyDerivation(
            Ontology sourceOntology,
            Concept root,
            PAreaTaxonomyFactory factory) {

        super(sourceOntology, factory);

        this.root = root;
    }
    
    public SimplePAreaTaxonomyDerivation(SimplePAreaTaxonomyDerivation derivation) {
        this(derivation.getSourceOntology(), derivation.getRoot(), derivation.getFactory());
    }

    public Concept getRoot() {
        return root;
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();

        PAreaTaxonomy taxonomy = generator.derivePAreaTaxonomy(
                super.getFactory(),
                super.getSourceOntology().getConceptHierarchy().getSubhierarchyRootedAt(root));

        return taxonomy;
    }

    @Override
    public String getDescription() {
        return String.format("Derived Partial-area Taxonomy (Root: %s)", root.getName());
    }
    
    @Override
    public JSONArray serializeToJSON() {        
        JSONArray result = new JSONArray();
        
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","SimplePAreaTaxonomyDerivation");       
        result.add(obj_class);
        
        //serialize root
        JSONObject obj_root = new JSONObject();
        obj_root.put("ConceptID", root.getIDAsString());   
        result.add(obj_root);
        
        return result;
    }
    
}
