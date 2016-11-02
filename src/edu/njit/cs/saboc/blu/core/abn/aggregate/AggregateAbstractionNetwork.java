package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 */
public interface AggregateAbstractionNetwork<ABN_T extends AbstractionNetwork> {
    public ABN_T getSource();
    
    public int getBound();
}
