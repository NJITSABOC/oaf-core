package edu.njit.cs.saboc.blu.core.abn.reduced;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 */
public interface ReducibleAbstractionNetwork<ABN_T extends AbstractionNetwork> {
    public boolean isReduced();
    
    public ABN_T getReduced(int smallest, int largest);
}
