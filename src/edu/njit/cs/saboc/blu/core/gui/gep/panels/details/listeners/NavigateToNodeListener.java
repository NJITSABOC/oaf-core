package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A listener for navigating to some kind of singly rooted node (e.g., partial-area) in the GEP
 * 
 * @author Chris O
 */
public class NavigateToNodeListener<T extends SinglyRootedNode> extends EntitySelectionAdapter<T> {

    private final EnhancedGraphExplorationPanel gep;
    
    public NavigateToNodeListener(EnhancedGraphExplorationPanel gep) {
        this.gep = gep;
    }
    
    @Override
    public void entityDoubleClicked(SinglyRootedNode group) {
        SinglyRootedNodeEntry entry = gep.getGraph().getNodeEntries().get(group);
        
        gep.centerOnEntry(entry);
        
        gep.highlightEntriesForSearch(new ArrayList<>(Arrays.asList(entry.getNode())));
    }
    
}
