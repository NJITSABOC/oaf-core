package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;

/**
 *
 * @author Chris O
 */
public class NavigateToContainerListener extends EntitySelectionAdapter<PartitionedNode> {

    private final EnhancedGraphExplorationPanel gep;

    public NavigateToContainerListener(EnhancedGraphExplorationPanel gep) {
        this.gep = gep;
    }

    @Override
    public void entityDoubleClicked(PartitionedNode container) {
        PartitionedNodeEntry entry = gep.getGraph().getContainerEntries().get(container);
        
        gep.focusOnPoint(entry.getX(), entry.getY());
    }
}
