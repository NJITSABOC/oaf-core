package edu.njit.cs.saboc.blu.core.abn.diff.change;

import edu.njit.cs.saboc.blu.core.abn.diff.AbstractionNetworkDiffResult;


/**
 *
 * @author Chris
 */
public class AbNChange {
    
    private AbstractionNetworkDiffResult diffAbN;
    
    protected AbNChange() {
        
    }
    
    public void setSourceDiffTaxonomy(AbstractionNetworkDiffResult diffAbN) {
        this.diffAbN = diffAbN;
    }
    
    public AbstractionNetworkDiffResult getSourceDiffAbN() {
        return diffAbN;
    }
}
