package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris O
 */
public class DiffAreaTaxonomy extends AreaTaxonomy<DiffArea> {
    
    private final DiffPAreaTaxonomyFactory diffFactory;
    
    private final AreaTaxonomy fromAreaTaxonomy;
    private final AreaTaxonomy toAreaTaxonomy;
    
    public DiffAreaTaxonomy(
            DiffPAreaTaxonomyFactory diffFactory,
            AreaTaxonomy fromAreaTaxonomy,
            AreaTaxonomy toAreaTaxonomy,
            Hierarchy<DiffArea> areaHierarchy) {
        
        super(toAreaTaxonomy.getPAreaTaxonomyFactory(), 
                areaHierarchy, 
                toAreaTaxonomy.getSourceHierarchy());
        
        this.diffFactory = diffFactory;
        
        this.fromAreaTaxonomy = fromAreaTaxonomy;
        this.toAreaTaxonomy = toAreaTaxonomy;
    }
    
    public DiffPAreaTaxonomyFactory getDiffFactory() {
        return diffFactory;
    }
    
    public AreaTaxonomy getFromAreaTaxonomy() {
        return fromAreaTaxonomy;
    }
    
    public AreaTaxonomy getToAreaTaxonomy() {
        return toAreaTaxonomy;
    }
}
