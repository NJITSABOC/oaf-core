package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AggregateAncestorTANDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation<T>
    implements RootedSubAbNDerivation<DisjointAbNDerivation>, AggregateAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation aggregateBase;
    private final int minBound;
    private final Concept selectedAggregatePAreaRoot;
    
    public AggregateAncestorTANDerivation(
            DisjointAbNDerivation aggregateBase, 
            int minBound,
            Concept selectedAggregatePAreaRoot) {
        
        super(aggregateBase);
        
        this.aggregateBase = aggregateBase;
        this.minBound = minBound;
        this.selectedAggregatePAreaRoot = selectedAggregatePAreaRoot;
    }
    
    public AggregateAncestorTANDerivation(AggregateAncestorTANDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), 
                deriveTaxonomy.getBound(), 
                deriveTaxonomy.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return selectedAggregatePAreaRoot;
    }

    @Override
    public DisjointAbNDerivation<T> getSuperAbNDerivation() {
        return aggregateBase;
    }

    @Override
    public int getBound() {
        return minBound;
    }

    @Override
    public DisjointAbNDerivation<T> getNonAggregateSourceDerivation() {
        AggregateAbNDerivation<DisjointAbNDerivation> derivedAggregate = (AggregateAbNDerivation<DisjointAbNDerivation>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        return String.format("Derived aggregate disjoint (%s)", selectedAggregatePAreaRoot.getName());
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        DisjointAbstractionNetwork aggregateDisjointAbN = this.getSuperAbNDerivation().getAbstractionNetwork();
        
        Set<DisjointNode> nodes = aggregateDisjointAbN.getNodesWith(selectedAggregatePAreaRoot);
        
        return aggregateDisjointAbN.getAncestorDisjointAbN(nodes.iterator().next());
    }
    
    @Override
    public JSONArray serializeToJSON() {
        JSONArray result = new JSONArray();
        result.add("AggregateAncestorTANDerivation");
        
        //serialzie aggregateBase
        JSONObject obj_aggregateBase = new JSONObject();
        obj_aggregateBase.put("BaseDerivation", aggregateBase.serializeToJSON());   
        result.add(obj_aggregateBase);

        //serialize minBound
        JSONObject obj_minBound = new JSONObject();
        obj_minBound.put("Bound", minBound);
        result.add(obj_minBound);
        
        //serialize selectedAggregatePAreaRoot
        JSONObject obj_selectedAggregatePAreaRoot = new JSONObject();
        obj_selectedAggregatePAreaRoot.put("ConceptID", selectedAggregatePAreaRoot.getID());
        result.add(obj_selectedAggregatePAreaRoot);
        
        return result;
    }    
}