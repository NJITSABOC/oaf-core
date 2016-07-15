package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class PAreaSubtaxonomy<T extends PArea> extends PAreaTaxonomy<T> {
    
    private final PAreaTaxonomy sourceTaxonomy;
    
    public PAreaSubtaxonomy(
            PAreaTaxonomy sourceTaxonomy,
            AreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy, 
            Hierarchy<Concept> conceptHierarchy) {

        super(areaTaxonomy, pareaHierarchy, conceptHierarchy);
        
        this.sourceTaxonomy = sourceTaxonomy;
    }
    
    public PAreaTaxonomy getSourceTaxonomy() {
        return sourceTaxonomy;
    }
}
