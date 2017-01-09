package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Chris O
 */
public abstract class AbNUIConfiguration<T extends Node> {
    
    private final AbNListenerConfiguration<T> listenerConfiguration;
    
    private final AbNDisplayManager abnDisplayManager;
    
    private AbNDisplayPanel abnDisplayPanel;
    
    protected AbNUIConfiguration(
            AbNDisplayManager abnDisplayManager,
            AbNListenerConfiguration<T> listenerConfiguration) {
        
        this.listenerConfiguration = listenerConfiguration;
        this.abnDisplayManager = abnDisplayManager;
    }
    
    public void setDisplayPanel(AbNDisplayPanel abnDisplayPanel) {
        this.abnDisplayPanel = abnDisplayPanel;
    }
    
    public AbNDisplayPanel getDisplayPanel() {
        return abnDisplayPanel;
    }
    
    public AbNDisplayManager getAbNDisplayManager() {
        return abnDisplayManager;
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
