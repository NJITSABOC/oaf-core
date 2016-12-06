package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.HierarchicalChanges;

/**
 *
 * @author Chris O
 */
public interface DiffAbNConceptChangesFactory {
    public DiffAbNConceptChanges getConceptChanges(HierarchicalChanges changes);
}
