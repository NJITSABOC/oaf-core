package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.listener;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public interface BLUPartitionedAbNListenerConfiguration extends BLUAbNListenerConfiguration {
    public EntitySelectionListener<ContainerReport> getContainerReportSelectedListener();
}
