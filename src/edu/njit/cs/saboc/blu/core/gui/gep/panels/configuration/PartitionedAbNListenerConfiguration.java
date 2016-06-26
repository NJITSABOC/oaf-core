package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public interface PartitionedAbNListenerConfiguration extends AbNListenerConfiguration {
    public EntitySelectionListener<ContainerReport> getContainerReportSelectedListener();
}
