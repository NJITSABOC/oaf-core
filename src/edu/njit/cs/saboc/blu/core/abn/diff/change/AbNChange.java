package edu.njit.cs.saboc.blu.core.abn.diff.change;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetwork;


/**
 *
 * @author Chris
 */
public class AbNChange {
    
    private DiffAbstractionNetwork diffAbN;
    
    protected AbNChange() {
        
    }
    
    public void setSourceDiffTaxonomy(DiffAbstractionNetwork diffAbN) {
        this.diffAbN = diffAbN;
    }
    
    public DiffAbstractionNetwork getSourceDiffAbN() {
        return diffAbN;
    }
}
