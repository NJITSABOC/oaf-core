package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

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
    
    public AggregateTargetAbNDerivation(TargetAbNDerivation nonAggregateSource, int bound) {
        super(nonAggregateSource);
        
        this.nonAggregateSource = nonAggregateSource;
        this.bound = bound;
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
        
        return targetAbN.getAggregated(bound);
    }

    @Override
    public String getDescription() {
        return String.format("%s (aggregated: %d)", super.getDescription(), bound);
    }

    @Override
    public String getName() {
        return String.format("%s (aggregated)", super.getName());
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName","AggregateTargetAbNDerivation");       
        result.put("BaseDerivation", nonAggregateSource.serializeToJSON());   
        result.put("Bound", bound);

        return result;
    }
}
