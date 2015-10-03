package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.GenericPArea;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.AggregatedGroupEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public abstract class GenericAreaAggregatedPAreaListModel<CONCEPT_T, 
        PAREA_T extends GenericPArea, 
        AGGREGATEPAREA_T extends GenericPArea & AggregateableConceptGroup<CONCEPT_T, PAREA_T>> extends BLUAbstractTableModel<AggregatedGroupEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T>> {

    protected final BLUGenericPAreaTaxonomyConfiguration configuration;
    
    public GenericAreaAggregatedPAreaListModel(BLUGenericPAreaTaxonomyConfiguration config) {
        super(new String[] {
           "Aggregated Partial-area",
            "From Area",
           String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)),
           "Aggregated Into"
        });
        
        this.configuration = config;
    }
    
    @Override
    protected Object[] createRow(AggregatedGroupEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T> item) {
        String aggregatedGroupName = configuration.getTextConfiguration().getGroupName(item.getAggregatedGroup());
        
        ArrayList<String> aggregatePAreas = new ArrayList<>();
        
        item.getAggregatedIntoGroups().forEach((AGGREGATEPAREA_T parea) -> {
            AggregateableConceptGroup<CONCEPT_T, PAREA_T> aggregatePArea = (AggregateableConceptGroup<CONCEPT_T, PAREA_T>)parea;
            
            aggregatePAreas.add(String.format("%s (%d) [%d]", configuration.getTextConfiguration().getGroupName(parea), aggregatePArea.getAllGroupsConcepts().size(),
                    aggregatePArea.getAggregatedGroups().size()));
        });
        
        Collections.sort(aggregatePAreas);
        
        String aggregatePAreasStr = aggregatePAreas.get(0);
        
        for(int c = 1; c < aggregatePAreas.size() ; c++) {
            aggregatePAreasStr += ("\n" + aggregatePAreas.get(c));
        }

        return new Object [] {
            aggregatedGroupName,
            getAggregatedPAreaAreaName(item.getAggregatedGroup()),
            item.getAggregatedGroup().getConceptsInPArea().size(),
            aggregatePAreasStr
        };
    }
    
    protected abstract String getAggregatedPAreaAreaName(PAREA_T aggregatedPArea);
}
