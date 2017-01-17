package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 * @param <T>
 */
public interface RootedSubAbNDerivation<T extends AbNDerivation> extends SubAbNDerivation<T> {
    public Concept getSelectedRoot();
}
