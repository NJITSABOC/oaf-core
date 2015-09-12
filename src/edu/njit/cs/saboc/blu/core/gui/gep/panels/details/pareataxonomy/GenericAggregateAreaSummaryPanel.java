package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericAggregateAreaSummaryPanel<CONCEPT_T,
        REL_T,
        TAXONOMY_T extends GenericPAreaTaxonomy,
        AREA_T extends GenericArea> extends GenericAreaSummaryPanel<CONCEPT_T, REL_T, TAXONOMY_T, AREA_T> {
    
    public GenericAggregateAreaSummaryPanel(GenericRelationshipPanel<REL_T> relTable, PAreaTaxonomyConfiguration configuration) {  
        super(relTable, configuration);
    }

    protected String createDescriptionStr(AREA_T area) {
        
        HashSet<GenericPArea> aggregatePAreas = new HashSet<>();
        HashSet<GenericPArea> regularPAreas = new HashSet<>();
        
        ArrayList<GenericPArea> areaPAreas = area.getAllPAreas();
        
        HashSet<CONCEPT_T> totalConcepts = new HashSet<>();
        
        areaPAreas.forEach( (GenericPArea parea) -> {
            AggregateableConceptGroup group = (AggregateableConceptGroup)parea;
            
            if(group.getAggregatedGroups().isEmpty()) {
                regularPAreas.add(parea);
            } else {
                aggregatePAreas.add(parea);
            }
            
            
            totalConcepts.addAll(group.getAllGroupsConcepts());
        });
        
        String pareaStr;
               
        if(aggregatePAreas.isEmpty()) {
            pareaStr = String.format("%d %s in %d regular partial-areas.",
                    totalConcepts.size(), configuration.getConceptTypeName(true),
                    regularPAreas.size());
        } else {
            pareaStr = String.format("%d %s in %d regular partial-area and %d aggregate partial-areas.",
                    totalConcepts.size(), configuration.getConceptTypeName(true),
                    regularPAreas.size(),
                    aggregatePAreas.size());
        }
        
        String areaName = configuration.getContainerName(area);
        
        return String.format("<html><b>%s</b> is an area that summarizes %s.",
                areaName, pareaStr, configuration.getConceptTypeName(true));
    }
}