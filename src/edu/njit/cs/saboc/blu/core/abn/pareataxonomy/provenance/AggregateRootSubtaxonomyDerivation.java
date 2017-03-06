package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

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
    
    public AggregateRootSubtaxonomyDerivation(
            PAreaTaxonomyDerivation aggregateBase, 
            int minBound,
            Concept selectedAggregatePAreaRoot) {
        
        super(aggregateBase);

        this.aggregateBase = aggregateBase;
        this.minBound = minBound;
        this.selectedAggregatePAreaRoot = selectedAggregatePAreaRoot;
    }
    
    public AggregateRootSubtaxonomyDerivation(AggregateRootSubtaxonomyDerivation derivedTaxonomy) {
        this(derivedTaxonomy.getSuperAbNDerivation(), derivedTaxonomy.getBound(), derivedTaxonomy.getSelectedRoot());
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
        AggregateAbNDerivation<PAreaTaxonomyDerivation> derivedAggregate = (AggregateAbNDerivation<PAreaTaxonomyDerivation>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        return String.format("Derived aggregate root subtaxonomy (PArea: %s)", selectedAggregatePAreaRoot.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomy sourceTaxonomy = this.getSuperAbNDerivation().getAbstractionNetwork();
        
        Set<PArea> pareas = sourceTaxonomy.getNodesWith(selectedAggregatePAreaRoot);
        
        return sourceTaxonomy.createRootSubtaxonomy(pareas.iterator().next());
    }
}
