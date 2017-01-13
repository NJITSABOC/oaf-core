
package edu.njit.cs.saboc.blu.core.abn.provenance;

/**
 *
 * @author Chris O
 * @param <T>
 */
public interface DerivedAggregateAbN<T extends DerivedAbstractionNetwork> {
    public int getBound();
    public T getNonAggregateSourceDerivation();
}
