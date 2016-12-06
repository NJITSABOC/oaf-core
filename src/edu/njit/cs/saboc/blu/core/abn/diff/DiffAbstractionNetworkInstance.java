package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChanges;

/**
 *
 * @author Chris O
 */
public interface DiffAbstractionNetworkInstance<T extends AbstractionNetwork> {
    public DiffAbNConceptChanges getOntologyDifferences();
    
    public T getFrom();
    public T getTo();
}
