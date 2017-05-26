package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris Ochs
 */
public class AggregateDescendantTargetAbNDerivation extends TargetAbNDerivation 
    implements RootedSubAbNDerivation<TargetAbNDerivation>, AggregateAbNDerivation<TargetAbNDerivation> {
    
    private final TargetAbNDerivation aggregateBase;
    private final int minBound;
    private final Concept selectedAggregateClusterRoot; 
    
    public AggregateDescendantTargetAbNDerivation(
            TargetAbNDerivation aggregateBase, 
            int minBound,
            Concept selectedAggregateClusterRoot) {
        
        super(aggregateBase);

        this.aggregateBase = aggregateBase;
        this.minBound = minBound;
        this.selectedAggregateClusterRoot = selectedAggregateClusterRoot;
    }
    
    public AggregateDescendantTargetAbNDerivation(
            AggregateDescendantTargetAbNDerivation derivedTargetAbN) {
        
        this(derivedTargetAbN.getSuperAbNDerivation(), 
                derivedTargetAbN.getBound(), 
                derivedTargetAbN.getSelectedRoot());
    }
    
    @Override
    public Concept getSelectedRoot() {
        return selectedAggregateClusterRoot;
    }

    @Override
    public TargetAbNDerivation getSuperAbNDerivation() {
        return aggregateBase;
    }

    @Override
    public int getBound() {
        return minBound;
    }

    @Override
    public TargetAbNDerivation getNonAggregateSourceDerivation() {
        AggregateAbNDerivation<TargetAbNDerivation> derivedAggregate = (AggregateAbNDerivation<TargetAbNDerivation>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        return String.format("Derived aggregate descendants target abstraction "
                + "network (Target Group: %s)", selectedAggregateClusterRoot.getName());
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        TargetAbstractionNetwork sourceTAN = this.getSuperAbNDerivation().getAbstractionNetwork(ontology);
        
        Set<TargetGroup> clusters = sourceTAN.getTargetGroupsWith(selectedAggregateClusterRoot);
        
        return sourceTAN.createDescendantTargetAbN(clusters.iterator().next());
    }
    
    @Override
    public String getName() {
        return String.format("%s %s", 
                selectedAggregateClusterRoot.getName(), 
                getAbstractionNetworkTypeName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Aggregate Descendants Target Abstraction Network";
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "AggregateDescendantTargetAbNDerivation");       
        result.put("BaseDerivation", aggregateBase.serializeToJSON());   
        result.put("Bound", minBound);
        result.put("ConceptID", selectedAggregateClusterRoot.getIDAsString());
        
        return result;
    }
}
