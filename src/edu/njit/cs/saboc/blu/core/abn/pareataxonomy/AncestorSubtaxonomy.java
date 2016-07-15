package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class AncestorSubtaxonomy<T extends PArea> extends PAreaSubtaxonomy<T> {
    
    private final T sourcePArea;
    
    public AncestorSubtaxonomy(
            PAreaTaxonomy<T> sourceTaxonomy,
            T sourcePArea,
            AreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy,
            Hierarchy<Concept> conceptHierarchy) {

        super(sourceTaxonomy, areaTaxonomy, pareaHierarchy, conceptHierarchy);
        
        this.sourcePArea = sourcePArea;
    }
    
    public T getSourcePArea() {
        return sourcePArea;
    }
}