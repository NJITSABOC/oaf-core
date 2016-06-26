package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;

/**
 *
 * @author Chris O
 */
public abstract class AbNUIConfiguration {
    
    private final AbNListenerConfiguration listenerConfiguration;
    
    private EnhancedGraphExplorationPanel gep;
    
    protected AbNUIConfiguration(AbNListenerConfiguration listenerConfiguration) {
        this.listenerConfiguration = listenerConfiguration;
    }
    
    public void setGEP(EnhancedGraphExplorationPanel gep) {
        this.gep = gep;
    }
    
    public EnhancedGraphExplorationPanel getGEP() {
        return gep;
    }
    
    public AbNListenerConfiguration getListenerConfiguration() {
        return listenerConfiguration;
    }
    
    public abstract AbstractNodeOptionsPanel getNodeOptionsPanel();
    
    public abstract ConceptPainter getConceptHierarchyPainter();
    
    public abstract AbstractAbNDetailsPanel createAbNDetailsPanel();
    
    public abstract boolean hasGroupDetailsPanel();
    public abstract NodeDashboardPanel createGroupDetailsPanel();
}
