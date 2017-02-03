package edu.njit.cs.saboc.nat.generic.gui.listeners;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.nat.generic.FocusConceptManager;

/**
 *
 * @author Chris O
 */
public class ConceptListNavigateSelectionAction implements FilterableListSelectionAction<Concept> {
    private final FocusConceptManager focusConcept;
    
    public ConceptListNavigateSelectionAction(FocusConceptManager focusConcept) {
        this.focusConcept = focusConcept;
    }
    
    public void handleEntrySelection(Concept c) {
        focusConcept.navigateTo(c);
    }
}
