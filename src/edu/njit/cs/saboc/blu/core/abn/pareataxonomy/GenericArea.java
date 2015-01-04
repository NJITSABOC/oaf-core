package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import SnomedShared.generic.GenericGroupContainer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericArea<CONCEPT_T, REL_T, 
        PAREA_T extends GenericPArea<CONCEPT_T, REL_T, PAREA_T>,
        REGION_T extends GenericRegion<CONCEPT_T, REL_T, PAREA_T>> extends GenericGroupContainer<REGION_T> {
    
    protected HashSet<REL_T> relationships;
    
    protected GenericArea(int id, HashSet<REL_T> relationships) {
        super(id);
        
        this.relationships = relationships;
    }
    
    public HashSet<REL_T> getRelationships() {
        return relationships;
    }
    
    public ArrayList<REGION_T> getRegions() {
        return partitions;
    }
    
    public void addPArea(PAREA_T parea) {
        boolean regionFound = false;
        
        for (GenericRegion<CONCEPT_T, REL_T, PAREA_T> region : partitions) {
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
    
    public HashSet<CONCEPT_T> getRoots() {
        HashSet<CONCEPT_T> roots = new HashSet<CONCEPT_T>();
        
        ArrayList<PAREA_T> pareas = this.getAllPAreas();
        
        for(PAREA_T parea : pareas) {
            roots.add(parea.getHierarchy().getRoot());
        }
        
        return roots;
    }
    
    public HashSet<CONCEPT_T> getConcepts() {
        HashSet<CONCEPT_T> concepts = new HashSet<CONCEPT_T>();
        
        ArrayList<PAREA_T> pareas = this.getAllPAreas();
        
        for(PAREA_T parea : pareas) {
            concepts.addAll(parea.getHierarchy().getNodesInHierarchy());
        }
        
        return concepts;
    }
    
    public ArrayList<PAREA_T> getAllPAreas() {
        ArrayList<PAREA_T> pareas = new ArrayList<PAREA_T>();

        for (REGION_T region : partitions) {
            pareas.addAll(region.getPAreasInRegion());
        }

        Collections.sort(pareas, new Comparator<PAREA_T>() {

            public int compare(PAREA_T a, PAREA_T b) {
                if (a.getConceptCount() == b.getConceptCount()) {
                    return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
                }

                return a.getConceptCount() > b.getConceptCount() ? -1 : 1;
            }
        });

        return pareas;
    }
    
    protected abstract REGION_T createRegion(PAREA_T parea);
   
    
}
