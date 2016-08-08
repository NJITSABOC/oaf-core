package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class DiffAreaTaxonomy extends AreaTaxonomy<DiffArea> {
    
    public DiffAreaTaxonomy(
            PAreaTaxonomyFactory factory,
            Hierarchy<DiffArea> areaHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {
        
        super(factory, areaHierarchy, sourceHierarchy);
    }
}
