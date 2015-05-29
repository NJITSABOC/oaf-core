package edu.njit.cs.saboc.nat.generic.gui.listeners;

import edu.njit.cs.saboc.nat.generic.FocusConcept;

/**
 *
 * @author Chris O
 */
public class ConceptListNavigateSelectionAction<T> implements FilterableListSelectionAction<T> {
    private FocusConcept<T> focusConcept;
    
    public ConceptListNavigateSelectionAction(FocusConcept<T> focusConcept) {
        this.focusConcept = focusConcept;
    }
    
    public void handleEntrySelection(T c) {
        focusConcept.navigate(c);
    }
}
