package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbNConceptChangesFactory;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.HierarchicalChanges;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyChanges;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.PropertyChangeDetailsFactory;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomyConceptChangesFactory implements DiffAbNConceptChangesFactory {
    
    private final PropertyChangeDetailsFactory propertyChangesFactory;
    
    public DiffPAreaTaxonomyConceptChangesFactory(PropertyChangeDetailsFactory propertyChangesFactory) {
        this.propertyChangesFactory = propertyChangesFactory;
    }

    @Override
    public DiffAbNConceptChanges getConceptChanges(HierarchicalChanges hierarchyChanges) {
        InheritablePropertyChanges propertyChanges = new InheritablePropertyChanges(hierarchyChanges, propertyChangesFactory);
        
        return new DiffPAreaTaxonomyConceptChanges(hierarchyChanges, propertyChanges);
    }
}
