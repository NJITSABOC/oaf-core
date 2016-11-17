package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 */
public interface AbNDerivationHistoryEntry<ABN_T extends AbstractionNetwork> {
    public String getDescription();
    
    public ABN_T getAbstractionNetwork();
}
