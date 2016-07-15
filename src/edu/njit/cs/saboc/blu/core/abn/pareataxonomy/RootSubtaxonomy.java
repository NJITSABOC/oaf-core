package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class RootSubtaxonomy<T extends PArea> extends PAreaSubtaxonomy<T> {

    public RootSubtaxonomy(
            PAreaTaxonomy<T> sourceTaxonomy,
            AreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy,
            Hierarchy<Concept> conceptHierarchy) {

        super(sourceTaxonomy, areaTaxonomy, pareaHierarchy, conceptHierarchy);
    }
    
    public T getSelectedRoot() {
        return super.getPAreaHierarchy().getRoot();
    }
}
