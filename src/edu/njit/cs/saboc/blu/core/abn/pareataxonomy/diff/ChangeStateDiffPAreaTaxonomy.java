package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.SubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class ChangeStateDiffPAreaTaxonomy extends DiffPAreaTaxonomy implements SubAbstractionNetwork<DiffPAreaTaxonomy> {
    
    private final DiffPAreaTaxonomy sourceTaxonomy;
    private final Set<ChangeState> includedStates;
    
    public ChangeStateDiffPAreaTaxonomy(
            DiffPAreaTaxonomy sourceTaxonomy, 
            DiffPAreaTaxonomy subsetTaxonomy,
            Set<ChangeState> includedStates) {
        
        super(subsetTaxonomy);
        
        this.sourceTaxonomy = sourceTaxonomy;
        this.includedStates = includedStates;
    }

    @Override
    public DiffPAreaTaxonomy getSuperAbN() {
        return sourceTaxonomy;
    }
    
    public DiffPAreaTaxonomy getSourceDiffTaxonomy() {
        return this.getSuperAbN();
    }
    
    public Set<ChangeState> getIncludedChangeStates() {
        return includedStates;
    }
}
