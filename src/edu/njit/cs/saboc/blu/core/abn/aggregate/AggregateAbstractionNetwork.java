package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 * @param <ABN_T>
 */
public interface AggregateAbstractionNetwork<T extends AggregateNode,
        ABN_T extends AbstractionNetwork> {
    
    public ABN_T getNonAggregateSourceAbN();
    public int getAggregateBound();
    public ABN_T expandAggregateNode(T node);
    
}
