package edu.njit.cs.saboc.nat.generic.gui.listeners;

import edu.njit.cs.saboc.nat.generic.FocusConceptManager;
import edu.njit.cs.saboc.nat.generic.data.NATConceptSearchResult;

/**
 *
 * @author Chris O
 */
public class SearchResultListNavigateSelectionAction implements FilterableListSelectionAction<NATConceptSearchResult> {
    private FocusConceptManager focusConcept;
    
    public SearchResultListNavigateSelectionAction(FocusConceptManager focusConcept) {
        this.focusConcept = focusConcept;
    }
    
    public void handleEntrySelection(NATConceptSearchResult sr) {
        focusConcept.navigateTo(sr.getConcept());
    }
}