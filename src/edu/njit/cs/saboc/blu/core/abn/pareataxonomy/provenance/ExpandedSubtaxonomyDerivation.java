package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author cro3
 */
public class ExpandedSubtaxonomyDerivation extends PAreaTaxonomyDerivation 
        implements SubAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final Concept aggregatePAreaRoot;
    private final PAreaTaxonomyDerivation base;
    
    public ExpandedSubtaxonomyDerivation(
            PAreaTaxonomyDerivation base, 
            Concept aggregatePAreaRoot) {
        
        super(base);
        
        this.base = base;
        this.aggregatePAreaRoot = aggregatePAreaRoot;
    }
    
    public ExpandedSubtaxonomyDerivation(ExpandedSubtaxonomyDerivation derivedTaxonomy) {
        this(derivedTaxonomy.getSuperAbNDerivation(), 
                derivedTaxonomy.getExpandedAggregatePAreaRoot());
    }
    
    public Concept getExpandedAggregatePAreaRoot() {
        return aggregatePAreaRoot;
    }

    @Override
    public PAreaTaxonomyDerivation getSuperAbNDerivation() {
        return base;
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomy baseAggregated = base.getAbstractionNetwork();
        
        AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy> aggregateTaxonomy = 
                (AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy>)baseAggregated;
        
        Set<AggregatePArea> pareas = baseAggregated.getNodesWith(aggregatePAreaRoot);
        
        return aggregateTaxonomy.expandAggregateNode(pareas.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("Expanded aggregate partial-area (%s)", aggregatePAreaRoot.getName());
    }
    
    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        result.add("ExpandedSubtaxonomyDerivation");
        
        //serialzie base
        JSONObject obj_base = new JSONObject();
        obj_base.put("BaseDerivation", base.serializeToJSON());   
        result.add(obj_base);

        //serialize aggregatePAreaRoot
        JSONObject obj_aggregatePAreaRoot = new JSONObject();
        obj_aggregatePAreaRoot.put("ConceptID", aggregatePAreaRoot.getID());
        result.add(obj_aggregatePAreaRoot);
        
        return result;
    }
    
}
