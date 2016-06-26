package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.AggregatedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public abstract class GenericAreaAggregatedPAreaListModel<CONCEPT_T, 
        PAREA_T extends PArea, 
        AGGREGATEPAREA_T extends PArea & AggregateNode<CONCEPT_T, PAREA_T>> extends OAFAbstractTableModel<AggregatedNodeEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T>> {

    protected final PAreaTaxonomyConfiguration configuration;
    
    public GenericAreaAggregatedPAreaListModel(PAreaTaxonomyConfiguration config) {
        super(new String[] {
           "Aggregated Partial-area",
            "From Area",
           String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)),
           "Aggregated Into"
        });
        
        this.configuration = config;
    }
    
    @Override
    protected Object[] createRow(AggregatedNodeEntry<CONCEPT_T, PAREA_T, AGGREGATEPAREA_T> item) {
        String aggregatedGroupName = configuration.getTextConfiguration().getGroupName(item.getAggregatedGroup());
        
        ArrayList<String> aggregatePAreas = new ArrayList<>();
        
        item.getAggregatedIntoGroups().forEach((AGGREGATEPAREA_T parea) -> {
            AggregateNode<CONCEPT_T, PAREA_T> aggregatePArea = (AggregateNode<CONCEPT_T, PAREA_T>)parea;
            
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
