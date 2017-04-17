package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an aggregate root sub TAN
 * 
 * @author Chris O
 */
public class AggregateRootSubTANDerivation extends ClusterTANDerivation 
    implements RootedSubAbNDerivation<ClusterTANDerivation>, AggregateAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation aggregateBase;
    private final int minBound;
    private final Concept selectedAggregateClusterRoot; 
    
    public AggregateRootSubTANDerivation(
            ClusterTANDerivation aggregateBase, 
            int minBound,
            Concept selectedAggregateClusterRoot) {
        
        super(aggregateBase);

        this.aggregateBase = aggregateBase;
        this.minBound = minBound;
        this.selectedAggregateClusterRoot = selectedAggregateClusterRoot;
    }
    
    public AggregateRootSubTANDerivation(AggregateRootSubTANDerivation derivedTaxonomy) {
        this(derivedTaxonomy.getSuperAbNDerivation(), derivedTaxonomy.getBound(), derivedTaxonomy.getSelectedRoot());
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
        return String.format("Derived aggregate root Sub TAN (Cluster: %s)", selectedAggregateClusterRoot.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        ClusterTribalAbstractionNetwork sourceTAN = this.getSuperAbNDerivation().getAbstractionNetwork();
        
        Set<Cluster> clusters = sourceTAN.getNodesWith(selectedAggregateClusterRoot);
        
        return sourceTAN.createRootSubTAN(clusters.iterator().next());
    }
    
    @Override
    public String getName() {
        return String.format("%s %s", 
                selectedAggregateClusterRoot.getName(), 
                getAbstractionNetworkTypeName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Aggregate Descendants Sub TAN";
    }

    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","AggregateRootSubTANDerivation");       
        result.add(obj_class);
        
        //serialzie aggregateBase
        JSONObject obj_aggregateBase = new JSONObject();
        obj_aggregateBase.put("BaseDerivation", aggregateBase.serializeToJSON());   
        result.add(obj_aggregateBase);

        //serialize minBound
        JSONObject obj_minBound = new JSONObject();
        obj_minBound.put("Bound", minBound);
        result.add(obj_minBound);
        
        //serialize selectedAggregateClusterRoot
        JSONObject obj_selectedAggregateClusterRoot = new JSONObject();
        obj_selectedAggregateClusterRoot.put("ConceptID", selectedAggregateClusterRoot.getIDAsString());
        result.add(obj_selectedAggregateClusterRoot);
        
        return result;
    }
}
