package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public abstract class AbNUIConfiguration<T extends Node> {
    
    private final AbNListenerConfiguration<T> listenerConfiguration;
    
    private AbNDisplayPanel abnDisplayPanel;
    
    protected AbNUIConfiguration(AbNListenerConfiguration<T> listenerConfiguration) {
        this.listenerConfiguration = listenerConfiguration;
    }
    
    public void setDisplayPanel(AbNDisplayPanel abnDisplayPanel) {
        this.abnDisplayPanel = abnDisplayPanel;
    }
    
    public AbNDisplayPanel getDisplayPanel() {
        return abnDisplayPanel;
    }
    
    public AbNListenerConfiguration<T> getListenerConfiguration() {
        return listenerConfiguration;
    }
    
    public abstract OAFAbstractTableModel<ParentNodeDetails<T>> getParentNodeTableModel();
    public abstract OAFAbstractTableModel<T> getChildNodeTableModel();
    
    public abstract NodeOptionsPanel<T> getNodeOptionsPanel();
    
    public abstract ConceptPainter getConceptHierarchyPainter();
    
    public abstract AbstractAbNDetailsPanel createAbNDetailsPanel();
    
    public abstract boolean hasGroupDetailsPanel();
    public abstract NodeDashboardPanel<T> createGroupDetailsPanel();
}
