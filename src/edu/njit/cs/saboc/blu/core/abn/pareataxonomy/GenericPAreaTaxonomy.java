package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
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

            extends PartitionedAbstractionNetwork<AREA_T, PAREA_T> implements AggregateableAbstractionNetwork<TAXONOMY_T> {

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
        
        TAXONOMY_T subtaxonomy = (TAXONOMY_T)generator.createTaxonomyFromPAreas(pareaIds, pareaSubhierarchy);
        
        if(this.isReduced()) {
            subtaxonomy.setReduced(isReduced);
        }
        
        return subtaxonomy;
    }
}
