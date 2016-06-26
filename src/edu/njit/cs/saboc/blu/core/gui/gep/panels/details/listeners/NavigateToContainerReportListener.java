package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public class NavigateToContainerReportListener extends EntitySelectionAdapter<ContainerReport> {

    private final EnhancedGraphExplorationPanel gep;

    public NavigateToContainerReportListener(EnhancedGraphExplorationPanel gep) {
        this.gep = gep;
    }

    @Override
    public void entityDoubleClicked(ContainerReport containerReport) {
        PartitionedNodeEntry entry = (PartitionedNodeEntry)gep.getGraph().getContainerEntries().get(containerReport.getContainer());
        
        gep.focusOnPoint(entry.getX(), entry.getY());
    }
}
