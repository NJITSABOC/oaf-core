package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class ExpandedSubtaxonomy extends PAreaSubtaxonomy {
    
    private final AggregatePArea aggregatePArea;
    
    public ExpandedSubtaxonomy(
            AggregatePAreaTaxonomy sourceTaxonomy,
            AggregatePArea aggregatePArea,
            AreaTaxonomy areaTaxonomy,
            Hierarchy<PArea> pareaHierarchy,
            Hierarchy<Concept> conceptHierarchy) {

        super(sourceTaxonomy, areaTaxonomy, pareaHierarchy, conceptHierarchy);
        
        this.aggregatePArea = aggregatePArea;
    }
    
    public AggregatePAreaTaxonomy getSourceTaxonomy() {
        return (AggregatePAreaTaxonomy)super.getSourceTaxonomy();
    }
    
    public AggregatePArea getAggregatePArea() {
        return aggregatePArea;
    }
}