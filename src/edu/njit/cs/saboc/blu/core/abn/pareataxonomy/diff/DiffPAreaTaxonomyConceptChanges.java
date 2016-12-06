package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.HierarchicalChanges;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyChanges;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomyConceptChanges extends DiffAbNConceptChanges {
    
    private final InheritablePropertyChanges propertyChanges;
    
    public DiffPAreaTaxonomyConceptChanges(
            HierarchicalChanges hierarchyChanges, 
            InheritablePropertyChanges propertyChanges) {
        
        super(hierarchyChanges);

        this.propertyChanges = propertyChanges;
    }
    
    public InheritablePropertyChanges getInheritablePropertyChanges() {
        return propertyChanges;
    }
}
