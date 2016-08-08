package edu.njit.cs.saboc.nat.generic.gui.listeners;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.FocusConcept;

/**
 *
 * @author Chris O
 */
public class ConceptListNavigateSelectionAction implements FilterableListSelectionAction<Concept> {
    private final FocusConcept focusConcept;
    
    public ConceptListNavigateSelectionAction(FocusConcept focusConcept) {
        this.focusConcept = focusConcept;
    }
    
    public void handleEntrySelection(Concept c) {
        focusConcept.navigate(c);
    }
}
