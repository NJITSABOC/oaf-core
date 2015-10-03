package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.listener.BLUAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbstractAbNDetailsPanel;

/**
 *
 * @author Chris O
 */
public abstract class BLUAbNUIConfiguration<
        ABN_T extends AbstractionNetwork, 
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T,
        CONFIG_T extends BLUConfiguration,
        T extends BLUAbNListenerConfiguration<ABN_T, GROUP_T, CONCEPT_T>> {
    
    private final T listenerConfiguration;
    
    private EnhancedGraphExplorationPanel gep;
    
    protected BLUAbNUIConfiguration(T listenerConfiguration) {
        this.listenerConfiguration = listenerConfiguration;
    }
    
    public void setGEP(EnhancedGraphExplorationPanel gep) {
        this.gep = gep;
    }
    
    public EnhancedGraphExplorationPanel getGEP() {
        return gep;
    }
    
    public T getListenerConfiguration() {
        return listenerConfiguration;
    }
    
    public abstract AbstractAbNDetailsPanel<ABN_T> createAbNDetailsPanel();
    
    
    public abstract boolean hasGroupDetailsPanel();
    public abstract AbstractNodePanel<GROUP_T, CONCEPT_T, CONFIG_T> createGroupDetailsPanel();
}
