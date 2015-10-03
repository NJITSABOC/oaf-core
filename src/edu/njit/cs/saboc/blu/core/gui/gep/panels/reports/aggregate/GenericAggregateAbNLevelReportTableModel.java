package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.aggregate;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableConceptGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.AbNLevelReport;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericAggregateAbNLevelReportTableModel<CONCEPT_T, GROUP_T extends GenericConceptGroup, CONTAINER_T extends GenericGroupContainer>  
        extends BLUAbstractTableModel<AbNLevelReport<CONCEPT_T, GROUP_T, CONTAINER_T>> {
    
     public GenericAggregateAbNLevelReportTableModel(BLUPartitionedConfiguration config) {
        super(new String [] { 
           "Level",
            String.format("# %s", config.getTextConfiguration().getContainerTypeName(true)),
            String.format("# Regular %s", config.getTextConfiguration().getGroupTypeName(true)),
            String.format("# Aggregate %s", config.getTextConfiguration().getGroupTypeName(true)),
            String.format("# Removed %s", config.getTextConfiguration().getGroupTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)),
        });
        
    }
    
    @Override
    protected Object[] createRow(AbNLevelReport<CONCEPT_T, GROUP_T, CONTAINER_T> item) {
        
        HashSet<GROUP_T> regularGroups = new HashSet<>();
        HashSet<GROUP_T> aggregateGroups = new HashSet<>();
        
        HashSet<GROUP_T> removedGroups = new HashSet<>();
        
        item.getGroupsAtLevel().forEach((GROUP_T group) -> {
            AggregateableConceptGroup aggregateGroup = (AggregateableConceptGroup)group;
            
            if(aggregateGroup.getAggregatedGroups().isEmpty()) {
                regularGroups.add(group);
            } else {
                aggregateGroups.add(group);
                
                removedGroups.addAll(aggregateGroup.getAggregatedGroups());
            }
        });
        
        return new Object [] {
            item.getLevel(),
            item.getContainersAtLevel().size(),
            regularGroups.size(),
            aggregateGroups.size(),
            removedGroups.size(),
            item.getOverlappingConceptsAtLevel().size()
        };
    }
}
