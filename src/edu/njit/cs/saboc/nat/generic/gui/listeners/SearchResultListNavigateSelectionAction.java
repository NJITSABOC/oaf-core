package edu.njit.cs.saboc.nat.generic.gui.listeners;

import edu.njit.cs.saboc.nat.generic.FocusConcept;
import edu.njit.cs.saboc.nat.generic.data.BrowserSearchResult;

/**
 *
 * @author Chris O
 */
public class SearchResultListNavigateSelectionAction implements FilterableListSelectionAction<BrowserSearchResult> {
    private FocusConcept focusConcept;
    
    public SearchResultListNavigateSelectionAction(FocusConcept focusConcept) {
        this.focusConcept = focusConcept;
    }
    
    public void handleEntrySelection(BrowserSearchResult sr) {
        focusConcept.navigate(sr.getConcept());
    }
}