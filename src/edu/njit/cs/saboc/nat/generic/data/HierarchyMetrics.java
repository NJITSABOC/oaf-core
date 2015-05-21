package edu.njit.cs.saboc.nat.generic.data;

import java.util.ArrayList;

/**
 *
 * @author Chris
 */
public class HierarchyMetrics {  
    private int ancestorCount;
    private int descendantCount;
    
    private int parentCount;
    private int childCount;
    
    private int siblingCount;
    
    private BrowserConcept focusConcept;
    
    public HierarchyMetrics(BrowserConcept focusConcept, int ancestorCount, int descendantCount, int parentCount, int childCount, int siblingCount) {
        this.focusConcept = focusConcept;
        this.ancestorCount = ancestorCount;
        this.descendantCount = descendantCount;
        this.parentCount = parentCount;
        this.childCount = childCount;
        this.siblingCount = siblingCount;
    }

    public int getAncestorCount() {
        return ancestorCount;
    }

    public int getDescendantCount() {
        return descendantCount;
    }

    public int getParentCount() {
        return parentCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public int getSiblingCount() {
        return siblingCount;
    }

    public BrowserConcept getFocusConcept() {
        return focusConcept;
    }
}
