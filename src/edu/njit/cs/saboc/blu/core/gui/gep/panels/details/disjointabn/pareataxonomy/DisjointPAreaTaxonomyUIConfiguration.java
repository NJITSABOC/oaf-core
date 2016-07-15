package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNUIConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class DisjointPAreaTaxonomyUIConfiguration extends DisjointAbNUIConfiguration<DisjointNode<PArea>> {
    public DisjointPAreaTaxonomyUIConfiguration(DisjointPAreaTaxonomyConfiguration config, 
            DisjointPAreaTaxonomyListenerConfiguration listenerConfig) {
        
        super(config, listenerConfig);
    }
}
