package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.HierarchicalChanges;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyChanges;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

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

    @Override
    public Set<DiffAbNConceptChange> getAllChangesFor(Concept c) {
        Set<DiffAbNConceptChange> changes = new HashSet<>(super.getHierarchicalChanges().getChangesFor(c));
        changes.addAll(propertyChanges.getPropertyDomainChangesFor(c));
        changes.addAll(propertyChanges.getPropertyHierarchyChangesFor(c));
        
        return changes;
    }
    
    
}
