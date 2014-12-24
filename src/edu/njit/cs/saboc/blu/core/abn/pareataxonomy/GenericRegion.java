package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import SnomedShared.generic.GenericContainerPartition;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericRegion<T, R, P extends GenericPArea<T,R>> extends GenericContainerPartition<P> {
    
    private HashSet<R> relationships;
    
    protected GenericRegion(P firstPArea) {
        addGroupToPartition(firstPArea);
        
        relationships = firstPArea.getRelationships();
    }
    
    public HashSet<R> getRelationships() {
        return relationships;
    }
    
    public ArrayList<P> getPAreasInRegion() {
        return groups;
    }
    
    public boolean isPAreaInRegion(P parea) {
        return parea.getRelationships().equals(this.getRelationships());
    }
    
    public void addPAreaToRegion(P parea) {
        addGroupToPartition(parea);
    }
}
