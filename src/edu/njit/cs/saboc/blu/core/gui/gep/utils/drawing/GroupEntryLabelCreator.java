package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import SnomedShared.generic.GenericConceptGroup;

/**
 *
 * @author Chris O
 */
public class GroupEntryLabelCreator<T extends GenericConceptGroup> {
    public String getRootNameStr(T group) {
        return group.getRoot().getName();
    }
    
    public String getCountStr(T group) {
        return String.format("(%d)", group.getConceptCount());
    }
}
