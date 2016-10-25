package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.ChildDisjointNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.DisjointNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.ParentDisjointNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public abstract class DisjointAbNUIConfiguration<T extends DisjointNode> extends AbNUIConfiguration<T> {
    
    private final DisjointAbNConfiguration<T> config;
    
    public DisjointAbNUIConfiguration(DisjointAbNConfiguration<T> config, DisjointAbNListenerConfiguration<T> listenerConfig) {
        super(listenerConfig);
        
        this.config = config;
    }

    @Override
    public OAFAbstractTableModel<ParentNodeDetails<T>> getParentNodeTableModel() {
        return new ParentDisjointNodeTableModel(config);
    }

    @Override
    public OAFAbstractTableModel<T> getChildNodeTableModel() {
        return new ChildDisjointNodeTableModel(config);
    }

    @Override
    public AbstractAbNDetailsPanel createAbNDetailsPanel() {
        return new AbstractAbNDetailsPanel<>(config);
    }

    @Override
    public boolean hasGroupDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel<T> createGroupDetailsPanel() {
        return new DisjointNodePanel(config);
    }
}