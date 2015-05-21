package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;

/**
 *
 * @author Chris
 */
public class FilterableConceptEntry extends Filterable<BrowserConcept> implements NavigableEntry {

    private BrowserConcept concept;

    public FilterableConceptEntry(BrowserConcept c) {
        this.concept = c;
    }

    public BrowserConcept getObject() {
        return concept;
    }

    public BrowserConcept getNavigableConcept() {
        return getObject();
    }

    protected String createEntryStr(String conceptName, String conceptId) {
        return String.format(
                "<html>%s <font color='blue'>(%s)</font>",
                conceptName, conceptId);
    }

    public String getInitialText() {
        return createEntryStr(concept.getName(), concept.getId());
    }

    public String getFilterText(String filter) {
        if (!filter.isEmpty()) {
            return createEntryStr(filter(concept.getName(), filter), filter(concept.getId(), filter));
        } else {
            return getInitialText();
        }
    }
}
