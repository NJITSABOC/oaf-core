package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an Aggregate Root Subtaxonomy
 * 
 * @author Chris O
 */
public class AggregateRootSubtaxonomyDerivation extends PAreaTaxonomyDerivation 
    implements RootedSubAbNDerivation<PAreaTaxonomyDerivation>, AggregateAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final PAreaTaxonomyDerivation aggregateBase;
    private final int minBound;
    private final Concept selectedAggregatePAreaRoot;
    private final boolean isWeightedAggregated;
    private final int autoScaleBound;
    private final boolean isAutoScaled;
    
    public AggregateRootSubtaxonomyDerivation(
            PAreaTaxonomyDerivation aggregateBase, 
            AggregatedProperty aggregatedProperty,
            Concept selectedAggregatePAreaRoot) {
        
        super(aggregateBase);

        this.aggregateBase = aggregateBase;
        this.minBound = aggregatedProperty.getBound();
        this.selectedAggregatePAreaRoot = selectedAggregatePAreaRoot;
        this.isWeightedAggregated = aggregatedProperty.getWeighted();
        this.autoScaleBound = aggregatedProperty.getAutoScaleBound();
        this.isAutoScaled = aggregatedProperty.getAutoScaled();
    }
    
    public AggregateRootSubtaxonomyDerivation(AggregateRootSubtaxonomyDerivation derivedTaxonomy) {
        this(derivedTaxonomy.getSuperAbNDerivation(), derivedTaxonomy.getAggregatedProperty(), derivedTaxonomy.getSelectedRoot());
    }
    
    @Override
    public Concept getSelectedRoot() {
        return selectedAggregatePAreaRoot;
    }

    @Override
    public PAreaTaxonomyDerivation getSuperAbNDerivation() {
        return aggregateBase;
    }

    @Override
    public int getBound() {
        return minBound;
    }

    @Override
    public PAreaTaxonomyDerivation getNonAggregateSourceDerivation() {
        AggregateAbNDerivation<PAreaTaxonomyDerivation> derivedAggregate = 
                (AggregateAbNDerivation<PAreaTaxonomyDerivation>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        if (isWeightedAggregated) {
            return String.format("Derived weighted aggregate root subtaxonomy (PArea: %s)", selectedAggregatePAreaRoot.getName());
        }
        return String.format("Derived aggregate root subtaxonomy (PArea: %s)", selectedAggregatePAreaRoot.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork(Ontology<Concept> ontology) {
        PAreaTaxonomy sourceTaxonomy = this.getSuperAbNDerivation().getAbstractionNetwork(ontology);
        
        Set<PArea> pareas = sourceTaxonomy.getNodesWith(selectedAggregatePAreaRoot);
        
        return sourceTaxonomy.createRootSubtaxonomy(pareas.iterator().next());
    }

    @Override
    public String getName() {
        return String.format("%s %s", 
                selectedAggregatePAreaRoot.getName(), 
                getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Aggregate Root Subtaxonomy";
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName","AggregateRootSubtaxonomyDerivation");       
        result.put("BaseDerivation", aggregateBase.serializeToJSON());   
        result.put("Bound", minBound);
        result.put("ConceptID", selectedAggregatePAreaRoot.getIDAsString());
        result.put("isWeightedAggregated", isWeightedAggregated);
        
        return result;
    }

    @Override
    public boolean isWeightedAggregated() {
        return isWeightedAggregated;
    }

    @Override
    public AggregatedProperty getAggregatedProperty() {
        return new AggregatedProperty(minBound, isWeightedAggregated);
    }
}
