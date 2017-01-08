package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 * @param <ABN_T>
 */
public interface AggregateableAbstractionNetwork<ABN_T extends AbstractionNetwork> {
    
    
    public boolean isAggregated();
    public ABN_T getAggregated(int smallestNode);
    
}
