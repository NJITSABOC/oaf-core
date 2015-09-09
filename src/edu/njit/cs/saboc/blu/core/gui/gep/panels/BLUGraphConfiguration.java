package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodePanel;

/**
 *
 * @author Chris
 */
public abstract class BLUGraphConfiguration {
    
    private EnhancedGraphExplorationPanel gep;
    
    private final String abnTypeName;

    public BLUGraphConfiguration(String abnTypeName) {
        this.abnTypeName = abnTypeName;
    }
    
    public String getAbNTypeName() {
        return abnTypeName;
    }
    
    public void setGEP(EnhancedGraphExplorationPanel gep) {
        this.gep = gep;
    }
    
    public EnhancedGraphExplorationPanel getGEP() {
        return gep;
    }
    
    public abstract boolean hasGroupDetailsPanel();
    public abstract AbstractNodePanel createGroupDetailsPanel();
    
    public abstract boolean hasContainerDetailsPanel();
    public abstract AbstractNodePanel createContainerDetailsPanel();
}
