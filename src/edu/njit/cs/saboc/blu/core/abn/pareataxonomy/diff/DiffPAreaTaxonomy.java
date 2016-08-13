package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomy extends PAreaTaxonomy<DiffPArea> {
    
    private final PAreaTaxonomy fromTaxonomy;
    private final PAreaTaxonomy toTaxonomy;
    
    public DiffPAreaTaxonomy(
            DiffAreaTaxonomy areaTaxonomy,
            PAreaTaxonomy fromTaxonomy,
            PAreaTaxonomy toTaxonomy,
            Hierarchy<DiffPArea> pareaHierarchy) {

        super(areaTaxonomy, pareaHierarchy, toTaxonomy.getSourceHierarchy());
        
        this.fromTaxonomy = fromTaxonomy;
        this.toTaxonomy = toTaxonomy;
    }
    
    public DiffAreaTaxonomy getAreaTaxonomy() {
        return (DiffAreaTaxonomy)super.getAreaTaxonomy();
    }
    
    public PAreaTaxonomy getFromPAreaTaxonomy() {
        return fromTaxonomy;
    }
    
    public PAreaTaxonomy getToPAreaTaxonomy() {
        return toTaxonomy;
    }
}
