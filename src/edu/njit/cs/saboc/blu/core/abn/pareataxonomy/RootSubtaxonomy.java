package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class RootSubtaxonomy extends PAreaSubtaxonomy {

    public RootSubtaxonomy(
            PAreaTaxonomy sourceTaxonomy,
            AreaTaxonomy areaTaxonomy,
            Hierarchy<PArea> pareaHierarchy,
            Hierarchy<Concept> conceptHierarchy) {

        super(sourceTaxonomy, areaTaxonomy, pareaHierarchy, conceptHierarchy);
    }
    
    public PArea getSelectedRoot() {
        return super.getPAreaHierarchy().getRoot();
    }
}
