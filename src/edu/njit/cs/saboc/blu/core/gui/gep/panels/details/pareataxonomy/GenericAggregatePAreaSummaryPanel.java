package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import java.util.HashSet;

/**
 *
 * @author cro3
 */
public class GenericAggregatePAreaSummaryPanel<CONCEPT_T, 
        REL_T, 
        TAXONOMY_T extends PAreaTaxonomy, 
        PAREA_T extends PArea, 
        AGGREGATEPAREA_T extends PArea & AggregateNode<CONCEPT_T, PAREA_T>> 
            extends PAreaSummaryPanel<CONCEPT_T, REL_T, TAXONOMY_T, AGGREGATEPAREA_T> {
    
    public GenericAggregatePAreaSummaryPanel(RelationshipPanel<REL_T> relationshipPanel, TAXONOMY_T taxonomy, PAreaTaxonomyConfiguration configuration) {
        super(relationshipPanel, taxonomy, configuration);
    }
    
    protected String createDescriptionStr(AGGREGATEPAREA_T group) {

        String rootName = configuration.getTextConfiguration().getGroupName(group);
        
        AggregateNode<CONCEPT_T, PAREA_T> aggregateGroup = (AggregateNode<CONCEPT_T, PAREA_T>)group;
         
        String typeDesc;
        
        if(aggregateGroup.getAggregatedGroups().isEmpty()) {
            PAREA_T parea = aggregateGroup.getAggregatedGroupHierarchy().getRoots().iterator().next();
            int conceptCount = parea.getConceptCount();
            
            typeDesc = String.format("<b>%s</b> is a regular partial-area that summarizes %d %s.",
                    rootName, parea.getConceptCount(), 
                    configuration.getTextConfiguration().getConceptTypeName(conceptCount > 1 || conceptCount == 0)).toLowerCase();
            
        } else {
            int aggregatedGroupCount = aggregateGroup.getAggregatedGroups().size();
            int totalConceptCount = aggregateGroup.getAllGroupsConcepts().size();
            
            typeDesc = String.format("<b>%s</b> is an aggregate partial-area that summarizes %d %s and %d %s.",
                    rootName, 
                    totalConceptCount, 
                    configuration.getTextConfiguration().getConceptTypeName(totalConceptCount > 1 || totalConceptCount == 0).toLowerCase(),
                    aggregatedGroupCount,
                    configuration.getTextConfiguration().getGroupTypeName(aggregatedGroupCount > 1 || aggregatedGroupCount == 0).toLowerCase());
        }

        int parentCount = taxonomy.getParentGroups(group).size();
        int childCount = taxonomy.getChildGroups(group).size();

        HashSet<PAREA_T> descendantPAreas = taxonomy.getDescendantGroups(group);

        return String.format("<html>%s It has %d parent partial-area(s) and %d child partial-area(s). "
                + "There are a total of %d descendant partial-area(s)."
                + "<p>"
                + "<b>Help / Description:</b><br>%s",
                typeDesc,  parentCount, childCount, descendantPAreas.size(), configuration.getTextConfiguration().getGroupHelpDescriptions(group));
    }
    
}
