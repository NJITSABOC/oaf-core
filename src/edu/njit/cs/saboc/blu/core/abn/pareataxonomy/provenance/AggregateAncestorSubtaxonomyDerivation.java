package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class AggregateAncestorSubtaxonomyDerivation extends PAreaTaxonomyDerivation 
    implements RootedSubAbNDerivation<PAreaTaxonomyDerivation>, AggregateAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final PAreaTaxonomyDerivation aggregateBase;
    private final int minBound;
    private final Concept selectedAggregatePAreaRoot;
    
    public AggregateAncestorSubtaxonomyDerivation(
            PAreaTaxonomyDerivation aggregateBase, 
            int minBound,
            Concept selectedAggregatePAreaRoot) {
        
        super(aggregateBase);
        
        this.aggregateBase = aggregateBase;
        this.minBound = minBound;
        this.selectedAggregatePAreaRoot = selectedAggregatePAreaRoot;
    }
    
    public AggregateAncestorSubtaxonomyDerivation(AggregateAncestorSubtaxonomyDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), 
                deriveTaxonomy.getBound(), 
                deriveTaxonomy.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return selectedAggregatePAreaRoot;
    }

    @Override
    public PAreaTaxonomyDerivation getSuperAbNDerivation() {
        return aggregateBase;
    }

    @Override
    public int getBound() {
        return minBound;
    }

    @Override
    public PAreaTaxonomyDerivation getNonAggregateSourceDerivation() {
        AggregateAbNDerivation<PAreaTaxonomyDerivation> derivedAggregate = (AggregateAbNDerivation<PAreaTaxonomyDerivation>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        return String.format("Derived aggregate ancestors subtaxonomy (PArea: %s)", selectedAggregatePAreaRoot.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomy sourceTaxonomy = this.getSuperAbNDerivation().getAbstractionNetwork();
        
        Set<PArea> pareas = sourceTaxonomy.getNodesWith(selectedAggregatePAreaRoot);
        
        return sourceTaxonomy.createAncestorSubtaxonomy(pareas.iterator().next());
    }
    
    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        result.add("AggregateAncestorSubtaxonomyDerivation");
        
        //serialzie aggregateBase
        JSONObject obj_aggregateBase = new JSONObject();
        obj_aggregateBase.put("BaseDerivation", aggregateBase.serializeToJSON());   
        result.add(obj_aggregateBase);

        //serialize minBound
        JSONObject obj_minBound = new JSONObject();
        obj_minBound.put("Bound", minBound);
        result.add(obj_minBound);
        
        //serialize selectedAggregatePAreaRoot
        JSONObject obj_selectedAggregatePAreaRoot = new JSONObject();
        obj_selectedAggregatePAreaRoot.put("ConceptID", selectedAggregatePAreaRoot.getIDAsString());
        result.add(obj_selectedAggregatePAreaRoot);
        
        return result;
    }
}
