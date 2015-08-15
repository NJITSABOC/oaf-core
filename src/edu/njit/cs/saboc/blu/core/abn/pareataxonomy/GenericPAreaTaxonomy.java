package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducedAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducedAbNHierarchy;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducibleAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducingGroup;
import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import edu.njit.cs.saboc.blu.core.abn.SingleRootedGroupHierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class GenericPAreaTaxonomy<
        TAXONOMY_T extends GenericPAreaTaxonomy<TAXONOMY_T, PAREA_T, AREA_T, REGION_T, CONCEPT_T, REL_T, HIERARCHY_T>,
        PAREA_T extends GenericPArea<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T>,
        AREA_T extends GenericArea<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T, REGION_T>,
        REGION_T extends GenericRegion<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T>,
        CONCEPT_T,
        REL_T,
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>> 

            extends PartitionedAbstractionNetwork<AREA_T, PAREA_T> implements ReducibleAbstractionNetwork<TAXONOMY_T> {
    
    protected PAREA_T rootPArea;

    protected HIERARCHY_T conceptHierarchy;
    
    protected boolean isReduced = false;
    
    protected GenericPAreaTaxonomy(
            HIERARCHY_T conceptHierarchy,
            PAREA_T rootPArea,
            ArrayList<AREA_T> areas,
            HashMap<Integer, PAREA_T> pareas,
            GroupHierarchy<PAREA_T> pareaHierarchy) {

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
    
    public HIERARCHY_T getConceptHierarchy() {
        return conceptHierarchy;
    }
    
    public PAREA_T getRootGroup() {
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
    
    protected TAXONOMY_T createRootSubtaxonomy(PAREA_T root, PAreaTaxonomyGenerator generator) {
        SingleRootedGroupHierarchy<PAREA_T> subhierarchy = (SingleRootedGroupHierarchy<PAREA_T>)this.groupHierarchy.getSubhierarchyRootedAt(root);
        
        GroupHierarchy<PAREA_T> pareaSubhierarchy = subhierarchy.asGroupHierarchy();
        
        HashSet<PAREA_T> pareas = pareaSubhierarchy.getNodesInHierarchy();
        
        HashMap<Integer, PAREA_T> pareaIds = new HashMap<>();
        
        pareas.forEach((PAREA_T parea) -> {
            pareaIds.put(parea.getId(), parea);
        });
        
        return createTaxonomyFromPAreas(generator, pareaIds, pareaSubhierarchy);
    }
    
    protected TAXONOMY_T createReducedTaxonomy(
            PAreaTaxonomyGenerator generator,
            ReducedAbNGenerator<PAREA_T> reducedGenerator, 
            int min,
            int max) {
        
        ReducedAbNHierarchy<PAREA_T> reducedPAreaHierarchy = reducedGenerator.createReducedAbN(rootPArea, this.getPAreas(), groupHierarchy, min, max);
        
        HashMap<Integer, PAREA_T> reducedPAreas = reducedPAreaHierarchy.reducedGroups;
        
        TAXONOMY_T reducedTaxonomy = createTaxonomyFromPAreas(generator, reducedPAreas, reducedPAreaHierarchy.reducedGroupHierarchy);
        
        reducedTaxonomy.setReduced(true);
        
        return reducedTaxonomy;
    }
    
    protected TAXONOMY_T createTaxonomyFromPAreas(PAreaTaxonomyGenerator generator, HashMap<Integer, PAREA_T> pareas, GroupHierarchy<PAREA_T> pareaHierarchy) {
             ArrayList<AREA_T> reducedAreas = new ArrayList<AREA_T>();
             
        int areaId = 0;
        
        HashMap<HashSet<REL_T>, AREA_T> areaMap = new HashMap<HashSet<REL_T>, AREA_T>();
        
        for(PAREA_T parea : pareas.values()) {
            AREA_T area;
            
            if(!areaMap.containsKey(parea.getRelsWithoutInheritanceInfo())) {
                area = (AREA_T)generator.createArea(areaId++, parea.getRelsWithoutInheritanceInfo());
                
                areaMap.put(area.getRelationships(), area);
                
                reducedAreas.add(area);
            } else {
                area = areaMap.get(parea.getRelsWithoutInheritanceInfo());
            }
            
            area.addPArea(parea);
            
            HashSet<GenericParentPAreaInfo<CONCEPT_T, PAREA_T>> reducedParentInfo = new HashSet<GenericParentPAreaInfo<CONCEPT_T, PAREA_T>>();
            
            PAREA_T originalRoot;
            
            if(parea instanceof ReducingGroup) {
                originalRoot = (PAREA_T)((ReducingGroup)parea).getReducedGroupHierarchy().getRoots().iterator().next();
            } else {
                originalRoot = parea;
            }

            HashSet<GenericParentPAreaInfo<CONCEPT_T, PAREA_T>> originalParents = originalRoot.getParentPAreaInfo();
                        
            for(GenericParentPAreaInfo<CONCEPT_T, PAREA_T> originalParent : originalParents) {
                if(pareas.containsKey(originalParent.getParentPArea().getId())) {
                    reducedParentInfo.add(originalParent);
                } else {
                    for(PAREA_T otherPArea : pareas.values()) {
                        if (otherPArea instanceof ReducingGroup) {
                            ReducingGroup reducedGroup = (ReducingGroup) otherPArea;

                            if (reducedGroup.getAllGroupsConcepts().contains(originalParent.getParentConcept())) {
                                reducedParentInfo.add(new GenericParentPAreaInfo<CONCEPT_T, PAREA_T>(originalParent.getParentConcept(), otherPArea));
                                break;
                            }
                        }
                    }
                }
            }

            parea.setParentPAreaInfo(reducedParentInfo);
        }
        
        TAXONOMY_T reducedTaxonomy = (TAXONOMY_T)generator.createPAreaTaxonomy(
                conceptHierarchy, 
                pareaHierarchy.getRoots().iterator().next(), 
                reducedAreas, 
                pareas, 
                pareaHierarchy);
        
        return reducedTaxonomy;
    }
    
}
