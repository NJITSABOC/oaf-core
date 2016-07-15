package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;

/**
 *
 * @author Chris O
 */
public abstract class DisjointPAreaTaxonomyListenerConfiguration extends DisjointAbNListenerConfiguration<DisjointNode<PArea>> {
    
    private final DisjointPAreaTaxonomyConfiguration config;
    
    public DisjointPAreaTaxonomyListenerConfiguration(DisjointPAreaTaxonomyConfiguration config) {
        this.config = config;
    }

    @Override
    public EntitySelectionListener<DisjointNode<PArea>> getChildGroupListener() {
        return new EntitySelectionAdapter<>();
    }

    @Override
    public EntitySelectionListener<ParentNodeDetails<DisjointNode<PArea>>> getParentGroupListener() {
        return new EntitySelectionAdapter<>();
    }
}
