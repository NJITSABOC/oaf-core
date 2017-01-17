
package edu.njit.cs.saboc.blu.core.abn.provenance;

/**
 *
 * @author Chris O
 * @param <T>
 */
public interface AggregateAbNDerivation<T extends AbNDerivation> {
    public int getBound();
    public T getNonAggregateSourceDerivation();
}
