
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an aggregate partial-area taxonomy 
 * 
 * @author Chris O
 */
public class AggregatePAreaTaxonomyDerivation extends PAreaTaxonomyDerivation 
        implements AggregateAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final PAreaTaxonomyDerivation nonAggregateSourceDerivation;
    private final AggregatedProperty ap;
    
    public AggregatePAreaTaxonomyDerivation(PAreaTaxonomyDerivation nonAggregateSourceDerivation, AggregatedProperty aggregatedProperty) {
        super(nonAggregateSourceDerivation);
        
        this.nonAggregateSourceDerivation = nonAggregateSourceDerivation;
        this.ap = aggregatedProperty;
    }
    
    public AggregatePAreaTaxonomyDerivation(AggregatePAreaTaxonomyDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getNonAggregateSourceDerivation(), deriveTaxonomy.getAggregatedProperty());
    }
    
    @Override
    public PAreaTaxonomyDerivation getNonAggregateSourceDerivation() {
        return nonAggregateSourceDerivation;
    }
    
    @Override
    public int getBound() {
        return ap.getBound();
    }

    @Override
    public String getDescription() {
        if (ap.getWeighted()) {
            return String.format("%s (weighted aggregated: %d)", nonAggregateSourceDerivation.getDescription(), ap.getBound());
        }
        return String.format("%s (aggregated: %d)", nonAggregateSourceDerivation.getDescription(), ap.getBound());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork(Ontology<Concept> ontology) {
        return getNonAggregateSourceDerivation().getAbstractionNetwork(ontology).getAggregated(ap);
    }
    
    @Override
    public String getName() {
        
        if (ap.getWeighted()) {
            return String.format("%s (Weighted Aggregated)", nonAggregateSourceDerivation.getName());
        }
        
        return String.format("%s (Aggregated)", nonAggregateSourceDerivation.getName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Aggregate Partial-area Taxonomy";
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "AggregatePAreaTaxonomyDerivation");       
        result.put("BaseDerivation", nonAggregateSourceDerivation.serializeToJSON());   
        result.put("Bound", ap.getBound());
        result.put("isWeightedAggregated", ap.getWeighted());
        result.put("AutoScaleBound", ap.getAutoScaleBound());
        result.put("isAutoScaled", ap.getAutoScaled());

        return result;
    }

    @Override
    public boolean isWeightedAggregated() {
        return ap.getWeighted();
    }

    @Override
    public AggregatedProperty getAggregatedProperty() {
        return ap;
    }
}
