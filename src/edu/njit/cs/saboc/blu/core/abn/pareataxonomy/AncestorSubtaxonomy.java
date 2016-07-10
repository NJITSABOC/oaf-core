package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AncestorSubtaxonomy extends PAreaSubtaxonomy {
    
    private final PArea sourcePArea;
    
    public AncestorSubtaxonomy(
            PAreaTaxonomy sourceTaxonomy,
            PArea sourcePArea,
            AreaTaxonomy areaTaxonomy,
            Hierarchy<PArea> pareaHierarchy,
            Hierarchy<Concept> conceptHierarchy) {

        super(sourceTaxonomy, areaTaxonomy, pareaHierarchy, conceptHierarchy);
        
        this.sourcePArea = sourcePArea;
    }
    
    public PArea getSourcePArea() {
        return sourcePArea;
    }
}