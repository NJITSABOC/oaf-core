package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.OverlappingConceptResult;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.data.BLUDisjointableAbNDataConfiguration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public abstract class BLUGenericPAreaTaxonomyDataConfiguration<TAXONOMY_T extends PAreaTaxonomy,
        DISJOINTTAXONOMY_T extends DisjointAbstractionNetwork<TAXONOMY_T, PAREA_T, CONCEPT_T, HIERARCHY_T, DISJOINTPAREA_T>,
        AREA_T extends Area,
        PAREA_T extends PArea,
        AGGREGATEPAREA_T extends PArea & AggregateNode<CONCEPT_T, PAREA_T>,
        DISJOINTPAREA_T extends DisjointNode<PAREA_T, CONCEPT_T, HIERARCHY_T, DISJOINTPAREA_T>, 
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T>,
        REL_T, 
        CONCEPT_T> implements BLUDisjointableAbNDataConfiguration<TAXONOMY_T, DISJOINTTAXONOMY_T, AREA_T, PAREA_T, DISJOINTPAREA_T, CONCEPT_T> {
    
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
        
        HashSet<OverlappingConceptResult<CONCEPT_T, PAREA_T>> overlappingConceptResults = getContainerOverlappingResults(area);
        
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