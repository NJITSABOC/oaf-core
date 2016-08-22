package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 */
public interface DiffAbstractionNetworkInstance<T extends AbstractionNetwork> {
    public OntologyDifferences getOntologyDifferences();
    
    public T getFrom();
    public T getTo();
}
