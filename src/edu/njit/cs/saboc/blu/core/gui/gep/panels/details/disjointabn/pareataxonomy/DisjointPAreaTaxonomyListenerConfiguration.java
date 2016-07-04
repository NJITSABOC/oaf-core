package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;

/**
 *
 * @author Chris O
 */
public abstract class DisjointPAreaTaxonomyListenerConfiguration extends DisjointAbNListenerConfiguration {
    
    private final DisjointPAreaTaxonomyConfiguration config;
    
    public DisjointPAreaTaxonomyListenerConfiguration(DisjointPAreaTaxonomyConfiguration config) {
        this.config = config;
    }

    @Override
    public EntitySelectionListener<Node> getChildGroupListener() {
        return new EntitySelectionAdapter<>();
    }

    @Override
    public EntitySelectionListener<ParentNodeDetails> getParentGroupListener() {
        return new EntitySelectionAdapter<>();
    }
}
