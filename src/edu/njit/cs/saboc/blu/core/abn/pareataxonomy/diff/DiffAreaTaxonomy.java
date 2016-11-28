package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetworkInstance;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyChanges;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 *
 * @author Chris O
 */
public class DiffAreaTaxonomy extends AreaTaxonomy<DiffArea> implements DiffAbstractionNetworkInstance<AreaTaxonomy> {
    
    private final DiffPAreaTaxonomyFactory diffFactory;
    
    private final InheritablePropertyChanges ontDifferences;
    
    private final AreaTaxonomy fromAreaTaxonomy;
    private final AreaTaxonomy toAreaTaxonomy;
    
    public DiffAreaTaxonomy(
            DiffPAreaTaxonomyFactory diffFactory,
            InheritablePropertyChanges ontDifferences,
            AreaTaxonomy fromAreaTaxonomy,
            AreaTaxonomy toAreaTaxonomy,
            Hierarchy<DiffArea> areaHierarchy) {
        
        super(toAreaTaxonomy.getPAreaTaxonomyFactory(), 
                areaHierarchy, 
                toAreaTaxonomy.getSourceHierarchy());
        
        this.diffFactory = diffFactory;
        
        this.ontDifferences = ontDifferences;
        
        this.fromAreaTaxonomy = fromAreaTaxonomy;
        this.toAreaTaxonomy = toAreaTaxonomy;
    }
    
    public DiffPAreaTaxonomyFactory getDiffFactory() {
        return diffFactory;
    }
    
    public InheritablePropertyChanges getOntologyDifferences() {
        return ontDifferences;
    }
    
    public AreaTaxonomy getFrom() {
        return fromAreaTaxonomy;
    }
    
    public AreaTaxonomy getTo() {
        return toAreaTaxonomy;
    }
}
