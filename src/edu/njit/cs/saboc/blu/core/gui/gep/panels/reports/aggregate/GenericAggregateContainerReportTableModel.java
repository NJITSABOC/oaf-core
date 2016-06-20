package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.aggregate;

import SnomedShared.generic.GenericConceptGroup;
import SnomedShared.generic.GenericGroupContainer;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.BLUPartitionedConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.BLUAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;
import java.util.HashSet;

/**
 *
 * @author Chris O
 */
public class GenericAggregateContainerReportTableModel<CONCEPT_T, 
        CONTAINER_T extends GenericGroupContainer, 
        GROUP_T extends GenericConceptGroup> 

    extends BLUAbstractTableModel<ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T>> {
    
    private final BLUPartitionedConfiguration config;
    
    public GenericAggregateContainerReportTableModel(BLUPartitionedConfiguration config) {
        
        super(new String[] {
            config.getTextConfiguration().getContainerTypeName(false),
            String.format("# Regular %s", config.getTextConfiguration().getGroupTypeName(true)),
            String.format("# Aggregate %s", config.getTextConfiguration().getGroupTypeName(true)),
            String.format("# Removed %s", config.getTextConfiguration().getGroupTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getConceptTypeName(true)),
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ContainerReport<CONTAINER_T, GROUP_T, CONCEPT_T> item) {
        
        HashSet<GROUP_T> regularGroups = new HashSet<>();
        HashSet<GROUP_T> aggregateGroups = new HashSet<>();
        
        HashSet<GROUP_T> removedGroups = new HashSet<>();
        
        item.getGroups().forEach((GROUP_T group) -> {
            AggregateNode aggregateGroup = (AggregateNode)group;
            
            if(aggregateGroup.getAggregatedGroups().isEmpty()) {
                regularGroups.add(group);
            } else {
                aggregateGroups.add(group);
                
                removedGroups.addAll(aggregateGroup.getAggregatedGroups());
            }
        });
        
        return new Object[] {
            config.getTextConfiguration().getContainerName(item.getContainer()).replaceAll(", ", "\n"),
            regularGroups.size(),
            aggregateGroups.size(),
            removedGroups.size(),
            item.getConcepts().size()
        };
    }
}
