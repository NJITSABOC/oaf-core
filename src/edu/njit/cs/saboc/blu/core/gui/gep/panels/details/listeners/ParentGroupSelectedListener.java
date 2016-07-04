package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;

/**
 *
 * @author Chris O
 */
public class ParentGroupSelectedListener extends EntitySelectionAdapter<ParentNodeDetails> {

    private final NavigateToNodeListener navigateOption;
    
    public ParentGroupSelectedListener(EnhancedGraphExplorationPanel gep) {
        navigateOption = new NavigateToNodeListener(gep);
    }
    
    @Override
    public void entityDoubleClicked(ParentNodeDetails entity) {
        navigateOption.entityDoubleClicked((SinglyRootedNode)entity.getParentNode());
    }
}
