package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.OntologyDifferences;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomyFactory {
    
    public DiffAreaTaxonomy createDiffAreaTaxonomy(
            OntologyDifferences ontDifferences,
            AreaTaxonomy fromSourceTaxonomy, 
            AreaTaxonomy toSourceTaxonomy, 
            Hierarchy<DiffArea> diffAreas) {
        
        return new DiffAreaTaxonomy(this, 
                ontDifferences,
                fromSourceTaxonomy,
                toSourceTaxonomy, 
                diffAreas);
    }
    
    public DiffPAreaTaxonomy createDiffPAreaTaxonomy(
            DiffAreaTaxonomy areaTaxonomy,
            PAreaTaxonomy fromSourceTaxonomy,
            PAreaTaxonomy toSourceTaxonomy,
            Hierarchy<DiffPArea> pareaHierarchy) {
        
        return new DiffPAreaTaxonomy(areaTaxonomy, 
                fromSourceTaxonomy, 
                toSourceTaxonomy, 
                pareaHierarchy);
    }
}
