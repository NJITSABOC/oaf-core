package edu.njit.cs.saboc.nat.generic.gui.filterablelist;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.nat.generic.data.BrowserConcept;
import edu.njit.cs.saboc.nat.generic.data.BrowserRelationship;

/**
 *
 * @author Chris
 */
public class FilterableRelationshipEntry extends Filterable<BrowserRelationship> implements NavigableEntry {
    
    private BrowserRelationship relationship;

    public FilterableRelationshipEntry(BrowserRelationship olr) {
        this.relationship = olr;
    }
    
    public BrowserRelationship getObject() {
        return relationship;
    }

    public BrowserConcept getNavigableConcept() {
        return relationship.getRelationshipTarget();
    }
    
    public String getInitialText() {
        return String.format("<html><font color='blue'>%s</font> => %s",
                relationship.getRelationshipName(),
                relationship.getRelationshipTarget().getName());
    }

    public String getFilterText(String filter) {
        return String.format("<html><font color='blue'>%s</font> => %s",
                filter(relationship.getRelationshipName(), filter),
                filter(relationship.getRelationshipTarget().getName(), filter));
    }
    
    public boolean containsFilter(String filter) {
        return relationship.getRelationshipName().toLowerCase().contains(filter) ||
                relationship.getRelationshipTarget().getName().toLowerCase().contains(filter);
    }
}
