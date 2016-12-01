package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public class NavigateToContainerReportListener extends EntitySelectionAdapter<ContainerReport> {

    private final AbNDisplayPanel displayPanel;

    public NavigateToContainerReportListener(AbNDisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
    }

    @Override
    public void entityDoubleClicked(ContainerReport containerReport) {
        PartitionedNodeEntry entry = displayPanel.getGraph().getContainerEntries().get(containerReport.getContainer());
        displayPanel.getAutoScroller().autoNavigateToNodeEntry(entry);
    }
}
