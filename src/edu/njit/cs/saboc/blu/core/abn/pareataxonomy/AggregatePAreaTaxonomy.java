package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaTaxonomy extends PAreaTaxonomy {

    private final PAreaTaxonomy sourceTaxonomy;
    
    private final int minBound;
    
    public AggregatePAreaTaxonomy(
            PAreaTaxonomy sourceTaxonomy,
            int minBound,
            AreaTaxonomy areaTaxonomy,
            Hierarchy<PArea> pareaHierarchy,
            Hierarchy<Concept> conceptHierarchy) {
    
        super(areaTaxonomy, pareaHierarchy, conceptHierarchy);
        
        this.sourceTaxonomy = sourceTaxonomy;
        this.minBound = minBound;
    }
    
    public PAreaTaxonomy getSourceTaxonomy() {
        return sourceTaxonomy;
    }
    
    public int getMinBound() {
        return minBound;
    }
    
    public ExpandedSubtaxonomy createExpandedSubtaxonomy(AggregatePArea parea) {
        AggregatePAreaTaxonomyGenerator generator = new AggregatePAreaTaxonomyGenerator();
        
        return generator.createExpandedSubtaxonomy(this, parea, new PAreaTaxonomyGenerator());
    }
}
