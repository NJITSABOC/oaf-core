package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import SnomedShared.generic.GenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducedAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducedAbNHierarchy;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducibleAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericPAreaTaxonomy<
        TAXONOMY_T extends GenericPAreaTaxonomy<TAXONOMY_T, PAREA_T, AREA_T, REGION_T, CONCEPT_T, REL_T>,
        PAREA_T extends GenericPArea<CONCEPT_T, REL_T, PAREA_T>,
        AREA_T extends GenericArea<CONCEPT_T, REL_T, PAREA_T, REGION_T>,
        REGION_T extends GenericRegion<CONCEPT_T, REL_T, PAREA_T>,
        CONCEPT_T,
        REL_T>  extends AbstractionNetwork
                implements ReducibleAbstractionNetwork<TAXONOMY_T> {
    
    protected PAREA_T rootPArea;

    protected SingleRootedHierarchy<CONCEPT_T> conceptHierarchy;
    
    protected boolean isReduced = false;
    
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
    
    public boolean isReduced() {
        return isReduced;
    }
    
    protected void setReduced(boolean reduced) {
        this.isReduced = reduced;
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
    
    public HashMap<Integer, PAREA_T> getPAreas() {
        return (HashMap<Integer, PAREA_T>)this.getGroups();
    }
    
    protected TAXONOMY_T createReducedTaxonomy(
            PAreaTaxonomyGenerator generator,
            ReducedAbNGenerator<PAREA_T> reducedGenerator, 
            int min,
            int max) {
        
        ReducedAbNHierarchy<PAREA_T> reducedPAreaHierarchy = reducedGenerator.createReducedAbN(rootPArea, this.getPAreas(), groupHierarchy, min, max);
        
        HashMap<Integer, PAREA_T> reducedPAreas = reducedPAreaHierarchy.reducedGroups;
        
        ArrayList<AREA_T> reducedAreas = new ArrayList<AREA_T>();
        int areaId = 0;
        
        HashMap<HashSet<REL_T>, AREA_T> areaMap = new HashMap<HashSet<REL_T>, AREA_T>();
        
        for(PAREA_T parea : reducedPAreas.values()) {
            AREA_T area;
            
            if(!areaMap.containsKey(parea.getRelsWithoutInheritanceInfo())) {
                area = (AREA_T)generator.createArea(areaId++, parea.getRelsWithoutInheritanceInfo());
                
                areaMap.put(area.getRelationships(), area);
                
                reducedAreas.add(area);
            } else {
                area = areaMap.get(parea.getRelsWithoutInheritanceInfo());
            }
            
            area.addPArea(parea);
        }
        
        TAXONOMY_T reducedTaxonomy = (TAXONOMY_T)generator.createPAreaTaxonomy(conceptHierarchy, reducedPAreas.get(rootPArea.getId()), reducedAreas, reducedPAreas, reducedPAreaHierarchy.reducedGroupHierarchy);
        
        reducedTaxonomy.setReduced(true);
        
        return reducedTaxonomy;
    }
    
}
