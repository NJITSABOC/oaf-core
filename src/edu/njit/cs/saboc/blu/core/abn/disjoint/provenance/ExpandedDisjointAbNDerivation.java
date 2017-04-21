package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate.AggregateDisjointNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an expanded disjoint abstraction network
 * 
 * @author Chris O
 */
public class ExpandedDisjointAbNDerivation extends DisjointAbNDerivation
        implements SubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final Concept expandedAggregateNodeRoot;
    
    public ExpandedDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            Concept expandedAggregateNodeRoot) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.expandedAggregateNodeRoot = expandedAggregateNodeRoot;
    }

    public Concept getExpandedAggregateNodeRoot() {
        return expandedAggregateNodeRoot;
    }
    
    @Override
    public DisjointAbNDerivation getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        DisjointAbstractionNetwork disjointAbN = getSuperAbNDerivation().getAbstractionNetwork();
        
         AggregateAbstractionNetwork<AggregateDisjointNode, DisjointAbstractionNetwork> aggregateDisjointAbN = 
                (AggregateAbstractionNetwork<AggregateDisjointNode, DisjointAbstractionNetwork>)disjointAbN;
        
        Set<DisjointNode> nodes = disjointAbN.getNodesWith(expandedAggregateNodeRoot);
        
        return aggregateDisjointAbN.expandAggregateNode((AggregateDisjointNode)nodes.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("Expanded %s", expandedAggregateNodeRoot.getName());
    }

    @Override
    public String getName() {
        return String.format("%s %s", expandedAggregateNodeRoot.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Expanded %s", sourceDisjointAbNDerivation.getAbstractionNetworkTypeName());
    }

    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        
        //serialize class
        JSONObject obj_class = new JSONObject();
        obj_class.put("ClassName","ExpandedDisjointAbNDerivation");       
        result.add(obj_class);
        
        //serialzie sourceDisjointAbNDerivation
        JSONObject obj_sourceDisjointAbNDerivation = new JSONObject();
        obj_sourceDisjointAbNDerivation.put("BaseDerivation", sourceDisjointAbNDerivation.serializeToJSON());   
        result.add(obj_sourceDisjointAbNDerivation);

        //serialize expandedAggregateNodeRoot
        JSONObject obj_expandedAggregateNodeRoot = new JSONObject();    
        obj_expandedAggregateNodeRoot.put("ConceptID", expandedAggregateNodeRoot.getIDAsString());
        result.add(obj_expandedAggregateNodeRoot);
        
        return result;       
    }           
}
