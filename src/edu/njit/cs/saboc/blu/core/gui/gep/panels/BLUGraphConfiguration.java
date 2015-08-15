package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodePanel;

/**
 *
 * @author Chris
 */
public abstract class BLUGraphConfiguration {

    public BLUGraphConfiguration() {

    }
    
    public abstract boolean hasGroupDetailsPanel();
    public abstract AbstractNodePanel createGroupDetailsPanel();
    
    public abstract boolean hasContainerDetailsPanel();
    public abstract AbstractNodePanel createContainerDetailsPanel();
}
