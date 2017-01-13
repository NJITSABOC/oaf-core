package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedAggregateAbN;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedRootedSubAbN;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DerivedAggregateAncestorSubtaxonomy extends DerivedPAreaTaxonomy 
    implements DerivedRootedSubAbN<DerivedPAreaTaxonomy>, DerivedAggregateAbN<DerivedPAreaTaxonomy> {
    
    private final DerivedPAreaTaxonomy aggregateBase;
    private final int minBound;
    private final Concept selectedAggregatePAreaRoot;
    
    public DerivedAggregateAncestorSubtaxonomy(
            DerivedPAreaTaxonomy aggregateBase, 
            int minBound,
            Concept selectedAggregatePAreaRoot) {
        
        super(aggregateBase);
        
        this.aggregateBase = aggregateBase;
        this.minBound = minBound;
        this.selectedAggregatePAreaRoot = selectedAggregatePAreaRoot;
    }
    
    public DerivedAggregateAncestorSubtaxonomy(DerivedAggregateAncestorSubtaxonomy deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), 
                deriveTaxonomy.getBound(), 
                deriveTaxonomy.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return selectedAggregatePAreaRoot;
    }

    @Override
    public DerivedPAreaTaxonomy getSuperAbNDerivation() {
        return aggregateBase;
    }

    @Override
    public int getBound() {
        return minBound;
    }

    @Override
    public DerivedPAreaTaxonomy getNonAggregateSourceDerivation() {
        DerivedAggregateAbN<DerivedPAreaTaxonomy> derivedAggregate = (DerivedAggregateAbN<DerivedPAreaTaxonomy>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        return String.format("Derived aggregate ancestors subtaxonomy (PArea: %s)", selectedAggregatePAreaRoot.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomy sourceTaxonomy = this.getSuperAbNDerivation().getAbstractionNetwork();
        
        Set<PArea> pareas = sourceTaxonomy.getNodesWith(selectedAggregatePAreaRoot);
        
        return sourceTaxonomy.createAncestorSubtaxonomy(pareas.iterator().next());
    }
}
