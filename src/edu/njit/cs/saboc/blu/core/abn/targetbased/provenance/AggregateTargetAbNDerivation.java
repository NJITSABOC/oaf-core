package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an aggregate target abstraction network
 * 
 * @author Chris O
 */
public class AggregateTargetAbNDerivation extends TargetAbNDerivation 
    implements AggregateAbNDerivation<TargetAbNDerivation> {
    
    private final TargetAbNDerivation nonAggregateSource;
    private final int bound;
    private final boolean isWeightedAggregated;
    
    public AggregateTargetAbNDerivation(
            TargetAbNDerivation nonAggregateSource, 
            AggregatedProperty aggregatedProperty) {
        
        super(nonAggregateSource);
        
        this.nonAggregateSource = nonAggregateSource;
        this.bound = aggregatedProperty.getBound();
        this.isWeightedAggregated = aggregatedProperty.getWeighted();
    }

    @Override
    public int getBound() {
        return bound;
    }

    @Override
    public TargetAbNDerivation getNonAggregateSourceDerivation() {
        return nonAggregateSource;
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        TargetAbstractionNetwork targetAbN = this.getNonAggregateSourceDerivation().getAbstractionNetwork(ontology);
        
        return targetAbN.getAggregated(bound, isWeightedAggregated);
    }

    @Override
    public String getDescription() {
        if (isWeightedAggregated) {
            return String.format("%s (weighted aggregated: %d)", super.getDescription(), bound);

        }
        return String.format("%s (aggregated: %d)", super.getDescription(), bound);
    }

    @Override
    public String getName() {
        if (isWeightedAggregated) {
            return String.format("%s (Weighted Aggregated)", super.getName());
        }
        return String.format("%s (Aggregated)", super.getName());
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName","AggregateTargetAbNDerivation");       
        result.put("BaseDerivation", nonAggregateSource.serializeToJSON());   
        result.put("Bound", bound);
        result.put("isWeightedAggregated", isWeightedAggregated);

        return result;
    }

    @Override
    public boolean isWeightedAggregated() {
        return isWeightedAggregated;
    }

    @Override
    public AggregatedProperty getAggregatedProperty() {
        return new AggregatedProperty(bound, isWeightedAggregated);
    }
}
