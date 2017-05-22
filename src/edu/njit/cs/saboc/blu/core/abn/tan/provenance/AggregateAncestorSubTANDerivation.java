package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an aggregate ancestor sub TAN
 * 
 * @author Chris O
 */
public class AggregateAncestorSubTANDerivation extends ClusterTANDerivation 
    implements RootedSubAbNDerivation<ClusterTANDerivation>, AggregateAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation aggregateBase;
    private final int minBound;
    private final Concept selectedAggregateClusterRoot;
    private final boolean isWeightedAggregated;
    
    public AggregateAncestorSubTANDerivation(
            ClusterTANDerivation aggregateBase, 
            int minBound,
            Concept selectedAggregateClusterRoot,
            boolean isWeightedAggregated) {
        
        super(aggregateBase);
        
        this.aggregateBase = aggregateBase;
        this.minBound = minBound;
        this.selectedAggregateClusterRoot = selectedAggregateClusterRoot;
        this.isWeightedAggregated = isWeightedAggregated;
    }
    
    public AggregateAncestorSubTANDerivation(AggregateAncestorSubTANDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), 
                deriveTaxonomy.getBound(), 
                deriveTaxonomy.getSelectedRoot(),
                deriveTaxonomy.isWeightedAggregated());
    }

    @Override
    public Concept getSelectedRoot() {
        return selectedAggregateClusterRoot;
    }

    @Override
    public ClusterTANDerivation getSuperAbNDerivation() {
        return aggregateBase;
    }

    @Override
    public int getBound() {
        return minBound;
    }

    @Override
    public ClusterTANDerivation getNonAggregateSourceDerivation() {
        AggregateAbNDerivation<ClusterTANDerivation> derivedAggregate = (AggregateAbNDerivation<ClusterTANDerivation>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        return String.format("Derived aggregate ancestors subtaxonomy (PArea: %s)", selectedAggregateClusterRoot.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        ClusterTribalAbstractionNetwork sourceTAN = this.getSuperAbNDerivation().getAbstractionNetwork();
        
        Set<Cluster> clusters = sourceTAN.getNodesWith(selectedAggregateClusterRoot);
        
        return sourceTAN.createAncestorTAN(clusters.iterator().next());
    }
    
    @Override
    public String getName() {
        return String.format("%s %s", 
                selectedAggregateClusterRoot.getName(), 
                getAbstractionNetworkTypeName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Aggregate Ancestor Sub TAN";
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "AggregateAncestorSubTANDerivation");       
        result.put("BaseDerivation", aggregateBase.serializeToJSON());   
        result.put("Bound", minBound);
        result.put("ConceptID", selectedAggregateClusterRoot.getIDAsString());
        result.put("isWeightedAggregated", isWeightedAggregated);
        
        return result;
    }   

    @Override
    public boolean isWeightedAggregated() {
        return isWeightedAggregated;
    }
}
