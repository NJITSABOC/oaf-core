package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.pravenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author cro3
 */
public class DerivedExpandedSubtaxonomy extends DerivedPAreaTaxonomy {
    
    private final Concept aggregatePAreaRoot;
    
    public DerivedExpandedSubtaxonomy(DerivedAggregatePAreaTaxonomy source, Concept aggregatePAreaRoot) {
        super(source);
        
        this.aggregatePAreaRoot = aggregatePAreaRoot;
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork() {
        AggregatePAreaTaxonomy taxonomy = (AggregatePAreaTaxonomy)super.getAbstractionNetwork();
        
        Set<AggregatePArea> pareas = taxonomy.getNodesWith(aggregatePAreaRoot);
        
        return taxonomy.expandAggregateNode(pareas.iterator().next());
    }
}
