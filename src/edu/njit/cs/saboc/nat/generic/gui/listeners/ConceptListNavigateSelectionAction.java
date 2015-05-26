package edu.njit.cs.saboc.nat.generic.gui.listeners;

import edu.njit.cs.saboc.nat.generic.FocusConcept;
import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;

/**
 *
 * @author Chris O
 */
public class ConceptListNavigateSelectionAction implements FilterableListSelectionAction<BrowserConcept> {
    private FocusConcept focusConcept;
    
    public ConceptListNavigateSelectionAction(FocusConcept focusConcept) {
        this.focusConcept = focusConcept;
    }
    
    public void handleEntrySelection(BrowserConcept c) {
        focusConcept.navigate(c);
    }
}
