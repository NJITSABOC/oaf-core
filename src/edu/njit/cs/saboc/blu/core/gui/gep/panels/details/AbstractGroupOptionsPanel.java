package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import SnomedShared.generic.GenericConceptGroup;

/**
 *
 * @author Chris O
 */
public abstract class AbstractGroupOptionsPanel<T extends GenericConceptGroup> extends GroupInformationPanel<T> {
    
    public AbstractGroupOptionsPanel() {
        
    }
    
    public abstract void enableOptionsForGroup(T group);
}
