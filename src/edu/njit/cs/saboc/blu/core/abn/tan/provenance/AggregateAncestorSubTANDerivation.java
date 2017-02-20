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
 *
 * @author Chris O
 */
public class AggregateAncestorSubTANDerivation extends ClusterTANDerivation 
    implements RootedSubAbNDerivation<ClusterTANDerivation>, AggregateAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation aggregateBase;
    private final int minBound;
    private final Concept selectedAggregateClusterRoot;
    
    public AggregateAncestorSubTANDerivation(
            ClusterTANDerivation aggregateBase, 
            int minBound,
            Concept selectedAggregateClusterRoot) {
        
        super(aggregateBase);
        
        this.aggregateBase = aggregateBase;
        this.minBound = minBound;
        this.selectedAggregateClusterRoot = selectedAggregateClusterRoot;
    }
    
    public AggregateAncestorSubTANDerivation(AggregateAncestorSubTANDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), 
                deriveTaxonomy.getBound(), 
                deriveTaxonomy.getSelectedRoot());
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
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","AggregateAncestorSubTANDerivation");       
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
