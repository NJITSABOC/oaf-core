package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericAggregateAreaSummaryPanel<CONCEPT_T,
        REL_T,
        TAXONOMY_T extends PAreaTaxonomy,
        AREA_T extends Area> extends AreaSummaryPanel<CONCEPT_T, REL_T, TAXONOMY_T, AREA_T> {
    
    public GenericAggregateAreaSummaryPanel(RelationshipPanel<REL_T> relTable, PAreaTaxonomyConfiguration configuration) {  
        super(relTable, configuration);
    }

    protected String createDescriptionStr(AREA_T area) {
        
        HashSet<PArea> aggregatePAreas = new HashSet<>();
        HashSet<PArea> regularPAreas = new HashSet<>();
        
        ArrayList<PArea> areaPAreas = area.getAllPAreas();
        
        HashSet<CONCEPT_T> totalConcepts = new HashSet<>();
        
        areaPAreas.forEach((PArea parea) -> {
            AggregateNode group = (AggregateNode)parea;
            
            if(group.getAggregatedGroups().isEmpty()) {
                regularPAreas.add(parea);
            } else {
                aggregatePAreas.add(parea);
            }

            totalConcepts.addAll(group.getAllGroupsConcepts());
        });
        
        String pareaStr;
               
        if(aggregatePAreas.isEmpty()) {
            pareaStr = String.format("%d %s in %d regular %s.",
                    totalConcepts.size(), 
                    configuration.getTextConfiguration().getConceptTypeName(true).toLowerCase(),
                    regularPAreas.size(),
                    configuration.getTextConfiguration().getGroupTypeName(regularPAreas.size() != 1).toLowerCase());
        } else {
            
            if(regularPAreas.isEmpty()) {
                pareaStr = String.format("%d %s in %d aggregate %s.",
                        totalConcepts.size(), 
                        configuration.getTextConfiguration().getConceptTypeName(true).toLowerCase(),
                        aggregatePAreas.size(),
                        configuration.getTextConfiguration().getGroupTypeName(aggregatePAreas.size() != 1).toLowerCase());
            } else {
                pareaStr = String.format("%d %s in %d regular %s and %d aggregate %s.",
                        totalConcepts.size(),
                        configuration.getTextConfiguration().getConceptTypeName(true).toLowerCase(),
                        regularPAreas.size(),
                        configuration.getTextConfiguration().getGroupTypeName(regularPAreas.size() != 1).toLowerCase(),
                        aggregatePAreas.size(),
                        configuration.getTextConfiguration().getGroupTypeName(aggregatePAreas.size() != 1).toLowerCase());
            }
        }

        String areaName = configuration.getTextConfiguration().getContainerName(area);
        
        return String.format("<html><b>%s</b> is an area that summarizes %s"
                + "<p><b>Help / Description:</b><br>%s",
                areaName, 
                pareaStr, 
                configuration.getTextConfiguration().getContainerHelpDescription(area));
    }
}