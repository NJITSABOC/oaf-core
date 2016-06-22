package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Chris O
 */
public class NavigateToGroupListener<GROUP_T extends GenericConceptGroup> extends EntitySelectionAdapter<GROUP_T> {

    private final EnhancedGraphExplorationPanel gep;
    
    public NavigateToGroupListener(EnhancedGraphExplorationPanel gep) {
        this.gep = gep;
    }
    
    @Override
    public void entityDoubleClicked(GROUP_T group) {
        SinglyRootedNodeEntry entry = gep.getGraph().getNodeEntries().get(group.getId());
        
        gep.centerOnEntry(entry);
        
        gep.highlightEntriesForSearch(new ArrayList<>(Arrays.asList(entry.getGroup())));
    }
    
}
