package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.listener.BLUAbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractNodePanel;

/**
 *
 * @author Chris O
 */
public abstract class BLUPartitionedAbNUIConfiguration<
        ABN_T extends AbstractionNetwork, 
        CONTAINER_T extends GenericGroupContainer,
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T,
        CONFIG_T extends BLUConfiguration,
        T extends BLUAbNListenerConfiguration<ABN_T, GROUP_T, CONCEPT_T>> extends BLUAbNUIConfiguration<ABN_T, GROUP_T, CONCEPT_T, CONFIG_T, T> {
    
    protected BLUPartitionedAbNUIConfiguration(T listenerConfiguration) {
        super(listenerConfiguration);
    }
    
    public abstract boolean hasContainerDetailsPanel();
    public abstract AbstractNodePanel<CONTAINER_T, CONCEPT_T, CONFIG_T> createContainerDetailsPanel();
}
