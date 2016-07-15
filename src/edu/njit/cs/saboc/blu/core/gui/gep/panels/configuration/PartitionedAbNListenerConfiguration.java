package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public interface PartitionedAbNListenerConfiguration<T extends SinglyRootedNode, V extends PartitionedNode> extends AbNListenerConfiguration<T> {
    public EntitySelectionListener<ContainerReport> getContainerReportSelectedListener();
}
