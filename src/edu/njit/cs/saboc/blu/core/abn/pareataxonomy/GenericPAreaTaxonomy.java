package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericPAreaTaxonomy<
        PAREA_T extends GenericPArea<CONCEPT_T, REL_T>,
        AREA_T extends GenericArea<CONCEPT_T, REL_T, PAREA_T, REGION_T>,
        REGION_T extends GenericRegion<CONCEPT_T, REL_T, PAREA_T>,
        CONCEPT_T,
        REL_T>  extends AbstractionNetwork {
    
    protected PAREA_T rootPArea;

    protected SingleRootedHierarchy<CONCEPT_T> conceptHierarchy;
    
    protected GenericPAreaTaxonomy(
            SingleRootedHierarchy<CONCEPT_T> conceptHierarchy,
            PAREA_T rootPArea,
            ArrayList<AREA_T> areas,
            HashMap<Integer, PAREA_T> pareas,
            HashMap<Integer, HashSet<Integer>> pareaHierarchy) {

        super(areas, pareas, pareaHierarchy);

        this.conceptHierarchy = conceptHierarchy;
                
        this.rootPArea = rootPArea;
    }
    
    public SingleRootedHierarchy<CONCEPT_T> getConceptHierarchy() {
        return conceptHierarchy;
    }
    
    public GenericConceptGroup getRootGroup() {
        return this.getRootPArea();
    }
    
    public PAREA_T getRootPArea() {
        return rootPArea;
    }
    
    public ArrayList<AREA_T> getAreas() {
        return (ArrayList<AREA_T>)this.getContainers();
    }
    
}
