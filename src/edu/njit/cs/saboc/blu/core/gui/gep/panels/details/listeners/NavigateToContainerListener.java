package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericContainerEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;

/**
 *
 * @author Chris O
 */
public class NavigateToContainerListener<CONTAINER_T extends GenericGroupContainer> extends EntitySelectionAdapter<CONTAINER_T> {

    private final EnhancedGraphExplorationPanel gep;

    public NavigateToContainerListener(EnhancedGraphExplorationPanel gep) {
        this.gep = gep;
    }

    @Override
    public void entityDoubleClicked(CONTAINER_T container) {
        GenericContainerEntry entry = (GenericContainerEntry)gep.getGraph().getContainerEntries().get(container.getId());
        
        gep.focusOnPoint(entry.getX(), entry.getY());
    }

}
