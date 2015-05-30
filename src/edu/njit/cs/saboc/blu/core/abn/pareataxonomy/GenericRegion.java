package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import SnomedShared.generic.GenericContainerPartition;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericRegion<
        CONCEPT_T, 
        REL_T, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        PAREA_T extends GenericPArea<CONCEPT_T,REL_T, HIERARCHY_T, PAREA_T>> extends GenericContainerPartition<PAREA_T> {
    
    private HashSet<REL_T> relationships;
    
    protected GenericRegion(PAREA_T firstPArea) {
        addGroupToPartition(firstPArea);
        
        relationships = firstPArea.getRelationships();
    }
    
    public HashSet<REL_T> getRelationships() {
        return relationships;
    }
    
    public ArrayList<PAREA_T> getPAreasInRegion() {
        return groups;
    }
    
    public boolean isPAreaInRegion(PAREA_T parea) {        
        return parea.getRelationships().equals(this.getRelationships());
    }

    public void addPAreaToRegion(PAREA_T parea) {
        addGroupToPartition(parea);
    }
}
