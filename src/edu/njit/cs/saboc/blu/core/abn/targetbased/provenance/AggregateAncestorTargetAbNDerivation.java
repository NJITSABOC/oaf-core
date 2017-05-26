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
public class AggregateAncestorTargetAbNDerivation extends TargetAbNDerivation 
    implements RootedSubAbNDerivation<TargetAbNDerivation>, AggregateAbNDerivation<TargetAbNDerivation> {
    
    private final TargetAbNDerivation aggregateBase;
    private final int minBound;
    private final Concept selectedAggregateTargetGroupRoot;
    
    public AggregateAncestorTargetAbNDerivation(
            TargetAbNDerivation aggregateBase, 
            int minBound,
            Concept selectedAggregateClusterRoot) {
        
        super(aggregateBase);
        
        this.aggregateBase = aggregateBase;
        this.minBound = minBound;
        this.selectedAggregateTargetGroupRoot = selectedAggregateClusterRoot;
    }
    
    public AggregateAncestorTargetAbNDerivation(AggregateAncestorTargetAbNDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), 
                deriveTaxonomy.getBound(), 
                deriveTaxonomy.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return selectedAggregateTargetGroupRoot;
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
        return String.format("Derived aggregate ancestors target abstraction network (Target Group: %s)", selectedAggregateTargetGroupRoot.getName());
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        TargetAbstractionNetwork sourceTargetAbN = this.getSuperAbNDerivation().getAbstractionNetwork(ontology);
        
        Set<TargetGroup> targetGroup = sourceTargetAbN.getTargetGroupsWith(selectedAggregateTargetGroupRoot);
        
        return sourceTargetAbN.createAncestorTargetAbN(targetGroup.iterator().next());
    }
    
    @Override
    public String getName() {
        return String.format("%s %s", 
                selectedAggregateTargetGroupRoot.getName(), 
                getAbstractionNetworkTypeName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Aggregate Ancestor Target Abstraction Network";
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "AggregateAncestorTargetAbNDerivation");       
        result.put("BaseDerivation", aggregateBase.serializeToJSON());   
        result.put("Bound", minBound);
        result.put("ConceptID", selectedAggregateTargetGroupRoot.getIDAsString());
        
        return result;
    }   
}
