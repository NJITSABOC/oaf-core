package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui;

import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.listener.BLUAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNNodeInformationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;

/**
 *
 * @author Chris O
 */
public abstract class BLUAbNUIConfiguration {
    
    private final BLUAbNListenerConfiguration listenerConfiguration;
    
    private EnhancedGraphExplorationPanel gep;
    
    protected BLUAbNUIConfiguration(BLUAbNListenerConfiguration listenerConfiguration) {
        this.listenerConfiguration = listenerConfiguration;
    }
    
    public void setGEP(EnhancedGraphExplorationPanel gep) {
        this.gep = gep;
    }
    
    public EnhancedGraphExplorationPanel getGEP() {
        return gep;
    }
    
    public BLUAbNListenerConfiguration getListenerConfiguration() {
        return listenerConfiguration;
    }
    
    public abstract AbstractAbNDetailsPanel createAbNDetailsPanel();
    
    public abstract boolean hasGroupDetailsPanel();
    public abstract AbNNodeInformationPanel createGroupDetailsPanel();
}
