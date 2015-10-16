package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericContainerEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public class NavigateToContainerReportListener<CONTAINER_T extends GenericGroupContainer, GROUP_T extends GenericConceptGroup, CONCEPT_T> 
extends EntitySelectionAdapter<ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T>> {

    private final EnhancedGraphExplorationPanel gep;

    public NavigateToContainerReportListener(EnhancedGraphExplorationPanel gep) {
        this.gep = gep;
    }

    @Override
    public void entityDoubleClicked(ContainerReport containerReport) {
        GenericContainerEntry entry = (GenericContainerEntry)gep.getGraph().getContainerEntries().get(containerReport.getContainer().getId());
        
        gep.focusOnPoint(entry.getX(), entry.getY());
        
        
    }
}
