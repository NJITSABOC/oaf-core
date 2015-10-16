package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.listener;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public interface BLUPartitionedAbNListenerConfiguration<
        ABN_T extends AbstractionNetwork, 
        CONTAINER_T extends GenericGroupContainer,
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T> extends BLUAbNListenerConfiguration<ABN_T, GROUP_T, CONCEPT_T> {
    
    public EntitySelectionListener<ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T>> getContainerReportSelectedListener();
}
