package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments for creating a relationship subtaxonomy
 * 
 * @author Chris O
 */
public class RelationshipSubtaxonomyDerivation extends PAreaTaxonomyDerivation 
        implements SubAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final Set<InheritableProperty> selectedProperties;
    private final PAreaTaxonomyDerivation base;
    
    public RelationshipSubtaxonomyDerivation(
            PAreaTaxonomyDerivation base, 
            Set<InheritableProperty> selectedProperties) {
        
        super(base);
        
        this.base = base;
        this.selectedProperties = selectedProperties;
    }
    
    public RelationshipSubtaxonomyDerivation(RelationshipSubtaxonomyDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), deriveTaxonomy.getSelectedProperties());
    }

    @Override
    public PAreaTaxonomyDerivation getSuperAbNDerivation() {
         return base;
    }
    
    public Set<InheritableProperty> getSelectedProperties() {
        return selectedProperties;
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        return base.getAbstractionNetwork().getRelationshipSubtaxonomy(selectedProperties);
    }

    @Override
    public String getDescription() {
        return "Created relationship subtaxonomy";
    }

    @Override
    public String getName() {
        if(base instanceof SimplePAreaTaxonomyDerivation) {
            String rootName = ((SimplePAreaTaxonomyDerivation)base).getRoot().getName();
            
            return String.format("%s %s", rootName, getAbstractionNetworkTypeName());
        }
        
        return "[Unknown Relationship Subtaxonomy Type]";
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Relationship Subtaxonomy";
    }
    
    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","RelationshipSubtaxonomyDerivation");       
        result.add(obj_class);
        
        //serialzie base
        JSONObject obj_base = new JSONObject();
        obj_base.put("BaseDerivation", base.serializeToJSON());   
        result.add(obj_base);

        //serialize selectedProperties
        JSONObject obj_selectedProperties = new JSONObject();
        JSONArray propertyids = new JSONArray();
        selectedProperties.forEach(sp -> {
            propertyids.add(sp.getIDAsString());
        }
        );
        obj_selectedProperties.put("PropertyIDs", propertyids);
        result.add(obj_selectedProperties);
        
        return result;
    }
}
