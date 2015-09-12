package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.nodes.DisjointGenericConceptGroup;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.OverlappingConceptResult;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUDisjointableAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyConfiguration<CONCEPT_T, 
        PAREA_T extends GenericPArea,
        AREA_T extends GenericArea,
        TAXONOMY_T extends GenericPAreaTaxonomy,
        DISJOINTPAREA_T extends DisjointGenericConceptGroup<PAREA_T, CONCEPT_T, HIERARCHY_T, DISJOINTPAREA_T>, 
        REL_T, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        DISJOINTTAXONOMY_T extends DisjointAbstractionNetwork<TAXONOMY_T, PAREA_T, CONCEPT_T, HIERARCHY_T, DISJOINTPAREA_T>,
        AGGREGATEPAREA_T extends GenericPArea & AggregateableConceptGroup<CONCEPT_T, PAREA_T>>

            implements BLUDisjointableAbNConfiguration<CONCEPT_T, PAREA_T, AREA_T, TAXONOMY_T, HIERARCHY_T, DISJOINTPAREA_T, DISJOINTTAXONOMY_T> {

    private final String CONTAINER_NAME = "Area";
    private final String GROUP_NAME = "Partial-area";
    private final String DISJOINT_GROUP_NAME = "Disjoint partial-area"; 
    
    @Override
    public String getDisjointGroupTypeName(boolean plural) {
        if(plural) {
            return DISJOINT_GROUP_NAME + "s";
        } else {
            return DISJOINT_GROUP_NAME;
        }
    }

    @Override
    public String getContainerTypeName(boolean plural) {
        if(plural) {
            return CONTAINER_NAME + "s";
        } else {
            return CONTAINER_NAME;
        }
    }

    @Override
    public String getGroupTypeName(boolean plural) {
        if(plural) {
            return GROUP_NAME + "s";
        } else {
            return GROUP_NAME;
        }
    }
    
    @Override
    public HashSet<PAREA_T> getContainerGroupSet(AREA_T area) {
        return new HashSet<>(area.getAllPAreas());
    }

    @Override
    public HashSet<CONCEPT_T> getGroupConceptSet(PAREA_T parea) {
        return new HashSet<>(parea.getConceptsInPArea());
    }

    @Override
    public HashSet<CONCEPT_T> getContainerOverlappingConcepts(AREA_T area) {
        HashSet<CONCEPT_T> areaOverlappingConcepts = new HashSet<>();
        
        HashSet<OverlappingConceptResult<CONCEPT_T, PAREA_T>> overlappingConceptResults = area.getOverlappingConcepts();
        
        overlappingConceptResults.forEach((OverlappingConceptResult<CONCEPT_T, PAREA_T> overlappingCls) -> {
            areaOverlappingConcepts.add(overlappingCls.getConcept());
        });
        
        return areaOverlappingConcepts;
    }

    @Override
    public int getContainerLevel(AREA_T area) {
        return area.getRelationships().size();
    }
    
    @Override
    public HashSet<OverlappingConceptResult<CONCEPT_T, PAREA_T>> getContainerOverlappingResults(AREA_T container) {
        return container.getOverlappingConcepts();
    }

    public abstract ArrayList<REL_T> getAreaRelationships(AREA_T area);
    public abstract ArrayList<REL_T> getPAreaRelationships(PAREA_T parea);
    public abstract Comparator<PAREA_T> getChildPAreaComparator();
    
    public abstract HIERARCHY_T getAggregatedPAreaHierarchy(AGGREGATEPAREA_T aggregatePArea);
    
    
}
