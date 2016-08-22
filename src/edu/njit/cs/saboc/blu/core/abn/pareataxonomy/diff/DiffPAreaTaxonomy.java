package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetworkInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.OntologyDifferences;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomy extends PAreaTaxonomy<DiffPArea> implements DiffAbstractionNetworkInstance<PAreaTaxonomy>{
    
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

    @Override
    public OntologyDifferences getOntologyDifferences() {
        return getAreaTaxonomy().getOntologyDifferences();
    }
    
    public PAreaTaxonomy getFrom() {
        return fromTaxonomy;
    }
    
    public PAreaTaxonomy getTo() {
        return toTaxonomy;
    }
}
