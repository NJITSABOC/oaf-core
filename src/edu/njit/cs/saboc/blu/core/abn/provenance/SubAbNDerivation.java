package edu.njit.cs.saboc.blu.core.abn.provenance;

/**
 *
 * @author Chris O
 */
public interface SubAbNDerivation<T extends AbNDerivation> {
    public T getSuperAbNDerivation();
}
