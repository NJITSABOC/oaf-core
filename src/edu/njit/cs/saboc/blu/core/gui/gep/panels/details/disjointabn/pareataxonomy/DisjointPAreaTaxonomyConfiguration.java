package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class DisjointPAreaTaxonomyConfiguration extends DisjointAbNConfiguration {
    
    public DisjointPAreaTaxonomyConfiguration(DisjointAbstractionNetwork<PAreaTaxonomy, PArea> disjointAbN) {
        super(disjointAbN);
    }
    
    public DisjointAbstractionNetwork<PAreaTaxonomy, PArea> getDisjointPAreaTaxonomy() {
        return (DisjointAbstractionNetwork<PAreaTaxonomy, PArea>)super.getAbstractionNetwork();
    }

}
