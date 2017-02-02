package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateCluster;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author cro3
 */
public class ExpandedSubTANDerivation extends ClusterTANDerivation 
        implements SubAbNDerivation<ClusterTANDerivation> {
    
    private final Concept aggregateClusterRoot;
    private final ClusterTANDerivation base;
    
    public ExpandedSubTANDerivation(
            ClusterTANDerivation base, 
            Concept aggregateClusterRoot) {
        
        super(base);
        
        this.base = base;
        this.aggregateClusterRoot = aggregateClusterRoot;
    }
    
    public ExpandedSubTANDerivation(ExpandedSubTANDerivation derivedTaxonomy) {
        this(derivedTaxonomy.getSuperAbNDerivation(), derivedTaxonomy.getExpandedAggregatePAreaRoot());
    }
    
    public Concept getExpandedAggregatePAreaRoot() {
        return aggregateClusterRoot;
    }

    @Override
    public ClusterTANDerivation getSuperAbNDerivation() {
        return base;
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        ClusterTribalAbstractionNetwork baseAggregated = base.getAbstractionNetwork();
        
        AggregateAbstractionNetwork<AggregateCluster, ClusterTribalAbstractionNetwork> aggregateTAN = 
                (AggregateAbstractionNetwork<AggregateCluster, ClusterTribalAbstractionNetwork>)baseAggregated;
        
        Set<AggregateCluster> pareas = baseAggregated.getNodesWith(aggregateClusterRoot);
        
        return aggregateTAN.expandAggregateNode(pareas.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("Expanded aggregate cluster (%s)", aggregateClusterRoot.getName());
    }

    @Override
    public JSONArray serializeToJSON() {        
        JSONArray result = new JSONArray();
        result.add("ExpandedSubTANDerivation");
        
        //serialzie base
        JSONObject obj_base = new JSONObject();
        obj_base.put("BaseDerivation", base.serializeToJSON());   
        result.add(obj_base);
        
        //serialzie aggregateClusterRoot
        JSONObject obj_aggregateClusterRoot = new JSONObject();
        obj_aggregateClusterRoot.put("ConceptID", aggregateClusterRoot.getID());   
        result.add(obj_aggregateClusterRoot);
        
        return result;
    }   
    
}
