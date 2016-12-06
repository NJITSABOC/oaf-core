package edu.njit.cs.saboc.blu.core.abn.diff.explain;

/**
 *
 * @author Chris O
 */
public class DiffAbNConceptChanges {
    private final HierarchicalChanges hierarchicalChanges;
    
    public DiffAbNConceptChanges(HierarchicalChanges hierarchicalChanges) {
        this.hierarchicalChanges = hierarchicalChanges;
    }
    
    public HierarchicalChanges getHierarchicalChanges() {
        return hierarchicalChanges;
    }
}
