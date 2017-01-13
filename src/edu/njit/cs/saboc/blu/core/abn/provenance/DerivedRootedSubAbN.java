package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 * @param <T>
 */
public interface DerivedRootedSubAbN<T extends DerivedAbstractionNetwork> extends DerivedSubAbN<T> {
    public Concept getSelectedRoot();
}
