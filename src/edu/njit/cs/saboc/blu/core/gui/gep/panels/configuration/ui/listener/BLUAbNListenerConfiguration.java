package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.ui.listener;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeInformation;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;

/**
 *
 * @author Chris O
 */
public interface BLUAbNListenerConfiguration<
        ABN_T extends AbstractionNetwork, 
        GROUP_T extends GenericConceptGroup, 
        CONCEPT_T> {
    
    public EntitySelectionListener<CONCEPT_T> getGroupConceptListListener();
    
    public EntitySelectionListener<GROUP_T> getChildGroupListener();
    
    public EntitySelectionListener<ParentNodeInformation<CONCEPT_T, GROUP_T>> getParentGroupListener();
}
