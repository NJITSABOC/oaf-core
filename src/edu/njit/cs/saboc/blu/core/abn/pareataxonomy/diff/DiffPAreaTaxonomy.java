package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomy extends PAreaTaxonomy {
    
    public DiffPAreaTaxonomy(
            DiffAreaTaxonomy areaTaxonomy,
            Hierarchy<PArea> pareaHierarchy, 
            Hierarchy<Concept> conceptHierarchy) {

        super(areaTaxonomy, pareaHierarchy, conceptHierarchy);
    }
    
    public DiffAreaTaxonomy getAreaTaxonomy() {
        return (DiffAreaTaxonomy)super.getAreaTaxonomy();
    }
}
