package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public abstract class DisjointAbNUIConfiguration extends AbNUIConfiguration {
    
    private final DisjointAbNConfiguration config;
    
    public DisjointAbNUIConfiguration(DisjointAbNConfiguration config, DisjointAbNListenerConfiguration listenerConfig) {
        super(listenerConfig);
        
        this.config = config;
    }

    @Override
    public OAFAbstractTableModel<ParentNodeDetails> getParentNodeTableModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OAFAbstractTableModel<Node> getChildNodeTableModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractAbNDetailsPanel createAbNDetailsPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasGroupDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createGroupDetailsPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
