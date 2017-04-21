package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetGroup;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class ExpandedTargetAbNDerivation extends TargetAbNDerivation 
        implements SubAbNDerivation<TargetAbNDerivation> {
    
    private final Concept aggregateTargetGroupRoot;
    private final TargetAbNDerivation base;
    
    public ExpandedTargetAbNDerivation(
            TargetAbNDerivation base, 
            Concept aggregateTargetGroupRoot) {
        super(base);
        
        this.base = base;
        this.aggregateTargetGroupRoot = aggregateTargetGroupRoot;
    }
    
    public ExpandedTargetAbNDerivation(ExpandedTargetAbNDerivation derivedTargetAbN) {
        this(derivedTargetAbN.getSuperAbNDerivation(), 
                derivedTargetAbN.getExpandedAggregatePAreaRoot());
    }
    
    public Concept getExpandedAggregatePAreaRoot() {
        return aggregateTargetGroupRoot;
    }

    @Override
    public TargetAbNDerivation getSuperAbNDerivation() {
        return base;
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork() {
        
        TargetAbstractionNetwork baseAggregated = base.getAbstractionNetwork();
        
        AggregateAbstractionNetwork<AggregateTargetGroup, TargetAbstractionNetwork> aggregateTargetAbN = 
                (AggregateAbstractionNetwork<AggregateTargetGroup, TargetAbstractionNetwork>)baseAggregated;
        
        Set<AggregateTargetGroup> pareas = baseAggregated.getNodesWith(aggregateTargetGroupRoot);
        
        return aggregateTargetAbN.expandAggregateNode(pareas.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("Expanded aggregate target abstraction network (%s)", 
                aggregateTargetGroupRoot.getName());
    }
    
    @Override
    public JSONArray serializeToJSON() {
 
        JSONArray result = new JSONArray();
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","ExpandedTargetAbNDerivation");       
        result.add(obj_class);

        
        //serialzie base
        JSONObject obj_base = new JSONObject();
        obj_base.put("BaseDerivation", base.serializeToJSON());   
        result.add(obj_base);

        //serialize aggregateTargetGroupRoot
        JSONObject obj_aggregateTargetGroupRoot = new JSONObject();
        obj_aggregateTargetGroupRoot.put("ConceptID", aggregateTargetGroupRoot.getIDAsString());
        result.add(obj_aggregateTargetGroupRoot);
               
        return result;
        
    }
    
}
