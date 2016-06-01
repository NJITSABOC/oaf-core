package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeInformation;
import edu.njit.cs.saboc.blu.core.gui.gep.EnhancedGraphExplorationPanel;

/**
 *
 * @author Chris O
 */
public class ParentGroupSelectedListener<CONCEPT_T, GROUP_T extends GenericConceptGroup>
        extends EntitySelectionAdapter<ParentNodeInformation<CONCEPT_T, GROUP_T>> {

    private final NavigateToGroupListener<GROUP_T> navigateOption;
    
    public ParentGroupSelectedListener(EnhancedGraphExplorationPanel gep) {
        navigateOption = new NavigateToGroupListener<>(gep);
    }
    
    @Override
    public void entityDoubleClicked(ParentNodeInformation<CONCEPT_T, GROUP_T> entity) {
        // TODO: Options for the parent?
        
        navigateOption.entityDoubleClicked(entity.getParentGroup());
    }
}
