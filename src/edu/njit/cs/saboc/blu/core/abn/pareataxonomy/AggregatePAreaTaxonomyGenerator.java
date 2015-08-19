package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.GroupHierarchy;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducedAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducedAbNHierarchy;
import edu.njit.cs.saboc.blu.core.abn.reduced.ReducingGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.SingleRootedHierarchy;
import java.util.HashMap;

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
        HIERARCHY_T extends SingleRootedHierarchy<CONCEPT_T, HIERARCHY_T>,
        AGGREGATEPAREA_T extends GenericPArea & ReducingGroup<CONCEPT_T, PAREA_T>> {
        
    public TAXONOMY_T createAggregatePAreaTaxonomy(
            TAXONOMY_T sourceTaxonomy,
            PAreaTaxonomyGenerator<TAXONOMY_T, PAREA_T, AREA_T, REGION_T, CONCEPT_T, REL_T, HIERARCHY_T> generator,
            ReducedAbNGenerator<PAREA_T, AGGREGATEPAREA_T> reducedGenerator, 
            int min,
            int max) {
        
        ReducedAbNHierarchy<PAREA_T, AGGREGATEPAREA_T> reducedPAreaHierarchy = reducedGenerator.createReducedAbN((PAREA_T)sourceTaxonomy.getRootPArea(), 
                sourceTaxonomy.getPAreas(), sourceTaxonomy.getGroupHierarchy(), min, max);

        HashMap<Integer, PAREA_T> reducedPAreas = (HashMap<Integer, PAREA_T>)reducedPAreaHierarchy.reducedGroups;
        
        TAXONOMY_T reducedTaxonomy = generator.createTaxonomyFromPAreas(reducedPAreas, (GroupHierarchy<PAREA_T>)reducedPAreaHierarchy.reducedGroupHierarchy);

        reducedTaxonomy.setReduced(true);

        return reducedTaxonomy;

    }
}
