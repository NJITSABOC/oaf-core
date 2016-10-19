package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;

/**
 *
 * @author Chris O
 */
public class ParentNodeSelectedListener<T extends SinglyRootedNode> 
    extends EntitySelectionAdapter<ParentNodeDetails<T>> {

    private final NavigateToNodeListener navigateOption;
    
    public ParentNodeSelectedListener(EnhancedGraphExplorationPanel gep) {
        navigateOption = new NavigateToNodeListener(gep);
    }
    
    @Override
    public void entityDoubleClicked(ParentNodeDetails<T> entity) {
        navigateOption.entityDoubleClicked(entity.getParentNode());
    }
}
