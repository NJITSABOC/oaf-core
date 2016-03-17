package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNResult;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaTaxonomyGenerator<
        TAXONOMY_T extends GenericPAreaTaxonomy<TAXONOMY_T, PAREA_T, AREA_T, REGION_T, CONCEPT_T, REL_T, HIERARCHY_T>,
        PAREA_T extends GenericPArea<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T>,
        AREA_T extends GenericArea<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T, REGION_T>,
        REGION_T extends GenericRegion<CONCEPT_T, REL_T, HIERARCHY_T, PAREA_T>,
        CONCEPT_T, // Concept type
        REL_T,// Relationship type
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T>,
        AGGREGATETAXONOMY_T extends GenericPAreaTaxonomy<TAXONOMY_T, PAREA_T, AREA_T, REGION_T, CONCEPT_T, REL_T, HIERARCHY_T>,
        AGGREGATEPAREA_T extends GenericPArea & AggregateableConceptGroup<CONCEPT_T, PAREA_T>> {
        
    public TAXONOMY_T createAggregatePAreaTaxonomy(
            final TAXONOMY_T sourceTaxonomy,
            final PAreaTaxonomyGenerator<TAXONOMY_T, PAREA_T, AREA_T, REGION_T, CONCEPT_T, REL_T, HIERARCHY_T> generator,
            final AggregateAbNGenerator<PAREA_T, AGGREGATEPAREA_T> reducedGenerator, 
            final int min) {
        
        if(min == 1) {
            return sourceTaxonomy;
        }
        
        AggregateAbNResult<PAREA_T, AGGREGATEPAREA_T> reducedPAreaHierarchy = reducedGenerator.createReducedAbN((PAREA_T)sourceTaxonomy.getRootPArea(), 
                sourceTaxonomy.getPAreas(), sourceTaxonomy.getGroupHierarchy(), min);

        HashMap<Integer, PAREA_T> reducedPAreas = (HashMap<Integer, PAREA_T>)reducedPAreaHierarchy.reducedGroups;
        
        TAXONOMY_T reducedTaxonomy = generator.createTaxonomyFromPAreas(reducedPAreas, (GroupHierarchy<PAREA_T>)reducedPAreaHierarchy.reducedGroupHierarchy);

        reducedTaxonomy.setReduced(true);

        return reducedTaxonomy;
    }
    
    public TAXONOMY_T createExpandedSubtaxonomy(AGGREGATEPAREA_T aggregatePArea, 
            PAreaTaxonomyGenerator<TAXONOMY_T, PAREA_T, AREA_T, REGION_T, CONCEPT_T, REL_T, HIERARCHY_T> generator) {
        
        AggregateableConceptGroup<CONCEPT_T, PAREA_T> aggregateInfo = (AggregateableConceptGroup<CONCEPT_T, PAREA_T>)aggregatePArea;
        
        GroupHierarchy<PAREA_T> groupHierarchy = aggregateInfo.getAggregatedGroupHierarchy();
        
        HashMap<Integer, PAREA_T> pareas = new HashMap<>();
        
        HashSet<PAREA_T> pareasInHierarchy = groupHierarchy.getNodesInHierarchy();
        
        pareasInHierarchy.forEach((PAREA_T parea) -> {
            pareas.put(parea.getId(), parea);
        });
        
        TAXONOMY_T expandedSubhierarchy = generator.createTaxonomyFromPAreas(pareas, groupHierarchy);
        
        return expandedSubhierarchy;
    }
}
