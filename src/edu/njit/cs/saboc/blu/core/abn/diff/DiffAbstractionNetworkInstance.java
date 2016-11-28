package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.OntologyChanges;

/**
 *
 * @author Chris O
 */
public interface DiffAbstractionNetworkInstance<T extends AbstractionNetwork> {
    public OntologyChanges getOntologyDifferences();
    
    public T getFrom();
    public T getTo();
}
