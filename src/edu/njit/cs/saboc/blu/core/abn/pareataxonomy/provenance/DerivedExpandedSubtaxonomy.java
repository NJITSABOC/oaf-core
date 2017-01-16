package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.provenance.DerivedSubAbN;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author cro3
 */
public class DerivedExpandedSubtaxonomy extends DerivedPAreaTaxonomy 
        implements DerivedSubAbN<DerivedPAreaTaxonomy> {
    
    private final Concept aggregatePAreaRoot;
    private final DerivedPAreaTaxonomy base;
    
    public DerivedExpandedSubtaxonomy(
            DerivedPAreaTaxonomy base, 
            Concept aggregatePAreaRoot) {
        
        super(base);
        
        this.base = base;
        this.aggregatePAreaRoot = aggregatePAreaRoot;
    }
    
    public DerivedExpandedSubtaxonomy(DerivedExpandedSubtaxonomy derivedTaxonomy) {
        this(derivedTaxonomy.getSuperAbNDerivation(), 
                derivedTaxonomy.getExpandedAggregatePAreaRoot());
    }
    
    public Concept getExpandedAggregatePAreaRoot() {
        return aggregatePAreaRoot;
    }

    @Override
    public DerivedPAreaTaxonomy getSuperAbNDerivation() {
        return base;
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        PAreaTaxonomy baseAggregated = base.getAbstractionNetwork();
        
        AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy> aggregateTaxonomy = 
                (AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy>)baseAggregated;
        
        Set<AggregatePArea> pareas = baseAggregated.getNodesWith(aggregatePAreaRoot);
        
        return aggregateTaxonomy.expandAggregateNode(pareas.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("Expanded aggregate partial-area (%s)", aggregatePAreaRoot.getName());
    }
    
}
