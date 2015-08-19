package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.reduced.AggregateableConceptGroup;
import java.util.HashSet;

/**
 *
 * @author cro3
 */
public class GenericAggregatePAreaSummaryPanel<CONCEPT_T, 
        REL_T, 
        TAXONOMY_T extends GenericPAreaTaxonomy, 
        PAREA_T extends GenericPArea, 
        AGGREGATEPAREA_T extends GenericPArea & AggregateableConceptGroup<CONCEPT_T, PAREA_T>> 
            extends GenericPAreaSummaryPanel<CONCEPT_T, REL_T, TAXONOMY_T, AGGREGATEPAREA_T> {
    
    public GenericAggregatePAreaSummaryPanel(GenericRelationshipPanel<REL_T> relationshipPanel, TAXONOMY_T taxonomy, PAreaTaxonomyConfiguration configuration) {
        super(relationshipPanel, taxonomy, configuration);
    }
    
    protected String createDescriptionStr(AGGREGATEPAREA_T group) {

        String rootName = configuration.getGroupName(group);
        
        AggregateableConceptGroup<CONCEPT_T, PAREA_T> aggregateGroup = ( AggregateableConceptGroup<CONCEPT_T, PAREA_T>)group;
         
        String typeDesc;
        
        if(aggregateGroup.getAggregatedGroups().isEmpty()) {
            PAREA_T parea = aggregateGroup.getAggregatedGroupHierarchy().getRoots().iterator().next();
            int conceptCount = parea.getConceptCount();
            
            typeDesc = String.format("<b>%s</b> is a regular partial-area that summarizes %d %s.",
                    rootName, parea.getConceptCount(), configuration.getConceptTypeName(conceptCount > 1 || conceptCount == 0));
            
        } else {
            int aggregatedGroupCount = aggregateGroup.getAggregatedGroups().size();
            int totalConceptCount = aggregateGroup.getAllGroupsConcepts().size();
            
            typeDesc = String.format("<b>%s</b> is an aggregate partial-area that summarizes %d %s and %d %s.",
                    rootName, 
                    totalConceptCount, 
                    configuration.getConceptTypeName(totalConceptCount > 1 || totalConceptCount == 0),
                    aggregatedGroupCount,
                    configuration.getGroupTypeName(aggregatedGroupCount > 1 || aggregatedGroupCount == 0));
        }

        int parentCount = taxonomy.getParentGroups(group).size();
        int childCount = taxonomy.getChildGroups(group).size();

        HashSet<PAREA_T> descendantPAreas = taxonomy.getDescendantGroups(group);


        return String.format("<html>%s It has %d parent partial-areas and %d child partial-areas. "
                + "There are a total of %d descendant partial-areas.",
                typeDesc,  parentCount, childCount, descendantPAreas.size());
    }
    
}
