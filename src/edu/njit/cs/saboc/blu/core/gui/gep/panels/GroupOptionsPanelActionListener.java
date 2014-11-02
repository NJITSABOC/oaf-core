package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import SnomedShared.generic.GenericConceptGroup;

/**
 *
 * @author Chris
 */
public interface GroupOptionsPanelActionListener<T extends GenericConceptGroup> {
    public void actionPerformedOn(T group);
}
