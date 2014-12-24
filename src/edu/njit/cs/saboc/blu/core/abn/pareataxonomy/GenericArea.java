package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import SnomedShared.generic.GenericGroupContainer;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericArea<T, R, 
        P extends GenericPArea<T, R>,
        REGION_T extends GenericRegion<T, R, P>> extends GenericGroupContainer<REGION_T> {
    
    protected HashSet<R> relationships;
    
    protected GenericArea(int id, HashSet<R> relationships) {
        super(id);
        
        this.relationships = relationships;
    }
    
    public HashSet<R> getRelationships() {
        return relationships;
    }
    
    public ArrayList<REGION_T> getRegions() {
        return partitions;
    }
    
    public void addPArea(P parea) {
        boolean regionFound = false;
        
        for (GenericRegion<T, R, P> region : partitions) {
            if (region.isPAreaInRegion(parea)) {
                regionFound = true;
                region.addPAreaToRegion(parea);
                break;
            }
        }

        if (!regionFound) {
            partitions.add(createRegion(parea));
        }
    }
    
    public HashSet<T> getRoots() {
        HashSet<T> roots = new HashSet<T>();
        
        // TODO: Get roots...
        
        return roots;
    }
    
    protected abstract REGION_T createRegion(P parea);
   
    
}
