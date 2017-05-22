
package edu.njit.cs.saboc.blu.core.abn.provenance;

/**
 * An interface for defining the derivation of some kind of 
 * aggregate abstraction network 
 * 
 * @author Chris O
 * @param <T>
 */
public interface AggregateAbNDerivation<T extends AbNDerivation> {
    
    /**
     * Return the bound that was used to create the aggregate abstraction network (>1)
     * @return 
     */
    public int getBound();
    
    /**
     * Return the flag that was used to create weighted or non-weighted aggregate abstraction network (>1)
     * @return 
     */
    public boolean isWeightedAggregated();
    
    /**
     * Returns the derivation used to create the non-aggregated version of the
     * abstraction network
     * @return 
     */
    public T getNonAggregateSourceDerivation();
}
